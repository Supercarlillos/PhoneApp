package com.phone.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.phone.app.domain.Phone;
import com.phone.app.repository.PhoneRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PhoneController.class)
public class PhoneControllerTest {

	private final ObjectMapper mapper = new ObjectMapper();

	@Autowired
	private MockMvc mvc;

	@MockBean
	private PhoneRepository phoneRepository;

	@Before
	public void before() throws MalformedURLException {
		given(this.phoneRepository.findAll()).willReturn(Lists.newArrayList(PhoneMother.list()));
	}

	@Test
	public void list() throws Exception {
		this.mvc.perform(get("/phones").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().string(mapper.writeValueAsString(PhoneMother.list())));
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

}
