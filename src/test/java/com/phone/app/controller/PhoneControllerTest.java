package com.phone.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.phone.app.domain.Phone;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest()
@WebAppConfiguration
public class PhoneControllerTest {

	private final ObjectMapper mapper = new ObjectMapper();

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mvc;

	@Before
	public void before() {
		this.mvc = MockMvcBuilders.webAppContextSetup(wac).build();
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
								.image("https:\\image_1.jpg").price(
								BigDecimal.valueOf(52)).build(),
						Phone.builder().name("Phone 2").id(2).description("Phone 2 description")
								.image("https:\\image_2.jpg").price(
								BigDecimal.valueOf(36.2)).build(),
						Phone.builder().name("Phone 3").id(3).description("Phone 3 description")
								.image("https:\\image_3.jpg").price(
								BigDecimal.valueOf(152)).build());
			} catch (MalformedURLException e) {
				//Unexpected
			}

			return Lists.newArrayList();
		}
	}

}
