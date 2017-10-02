package com.phone.app;

import com.google.common.collect.Lists;
import com.phone.app.domain.Phone;
import com.phone.app.repository.PhoneRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@SpringBootApplication
public class PhoneAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhoneAppApplication.class, args);
	}

	/**
	 * Insert several phones, only for test purpose
	 * @param phoneRepository
	 * @return
	 */
	@Bean
	CommandLineRunner runner(final PhoneRepository phoneRepository) {
		return args -> {
			phoneRepository.save(Lists.newArrayList(Phone.builder().name("Phone 1").description("Phone 1 description")
							.image("https:\\image_1.jpg").price(
							BigDecimal.valueOf(52)).build(),
					Phone.builder().name("Phone 2").description("Phone 2 description")
							.image("https:\\image_2.jpg").price(
							BigDecimal.valueOf(36.2)).build(),
					Phone.builder().name("Phone 3").description("Phone 3 description")
							.image("https:\\image_3.jpg").price(
							BigDecimal.valueOf(152)).build()));

		};
	}

}
