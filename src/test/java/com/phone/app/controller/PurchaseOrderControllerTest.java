package com.phone.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.phone.app.domain.Phone;
import com.phone.app.domain.PurchaseOrder;
import com.phone.app.repository.PhoneRepository;
import com.phone.app.repository.PurchaseOrderRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.collections.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PurchaseOrderController.class)
public class PurchaseOrderControllerTest {

	private final ObjectMapper mapper = new ObjectMapper();

	@Autowired
	private MockMvc mvc;

	@MockBean
	private PurchaseOrderRepository purchaseOrderRepository;

	@MockBean
	private PhoneRepository phoneRepository;

	@MockBean
	private RestTemplate restTemplate;

	private JacksonTester<PurchaseOrder> jsonTester;

	private HttpMessageConverter mappingJackson2HttpMessageConverter;

	@Before
	public void before() throws MalformedURLException {

		Phone[] phones ={};
		JacksonTester.initFields(this, mapper);
		given(this.purchaseOrderRepository.findAll())
				.willReturn(Lists.newArrayList(PurchaseOrderMother.list()));
		given(this.purchaseOrderRepository.findOne(1))
				.willReturn(PurchaseOrderMother.purchaseOrder());
		given(restTemplate.getForObject("http://127.0.0.1:8080/phones", Phone[].class))
				.willReturn(PhoneMother.list().toArray(phones));
	}

	@Test
	public void list() throws Exception {
		this.mvc.perform(get("/purchase_orders").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().string(mapper.writeValueAsString(PurchaseOrderMother.list())));
	}

	@Test
	public void create() throws Exception {
		this.mvc.perform(post("/purchase_orders")
				.content(jsonTester.write(PurchaseOrderMother.purchaseOrder()).getJson())
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk());
	}

	@Test
	public void total() throws Exception {
		this.mvc.perform(get("/purchase_orders/1/total").accept(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk()).andExpect(content().string("52.00"));
	}

	private static class PurchaseOrderMother {

		public static PurchaseOrder purchaseOrder() {
			return PurchaseOrder.builder().id(1).name("Name").surname("Surname").email("email@emai.com")
					.phones(PhoneMother.list()).build();
		}

		public static List<PurchaseOrder> list() {
			return Lists.newArrayList(purchaseOrder());
		}
	}

	private static class PhoneMother {

		public static List<Phone> list() {
			try {
				return Lists.newArrayList(Phone.builder().id(1).name("Phone 1").description("Phone 1 description")
						.image("https://image_1.jpg").price(
								BigDecimal.valueOf(52)).build());
			} catch (MalformedURLException e) {
				//Unexpected
			}

			return Lists.newArrayList();
		}
	}

	protected String json(Object o) throws IOException {
		MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
		this.mappingJackson2HttpMessageConverter.write(
				o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
		return mockHttpOutputMessage.getBodyAsString();
	}

	@Autowired
	void setConverters(HttpMessageConverter<?>[] converters) {
		this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
				.filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
				.findAny()
				.orElse(null);
	}

}
