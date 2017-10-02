package com.phone.app.controller;

import com.phone.app.domain.Phone;
import com.phone.app.repository.PhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/phones", produces = "application/json")
public class PhoneController {

	private final PhoneRepository phoneRepository;

	@Autowired
	public PhoneController(PhoneRepository phoneRepository) {
		this.phoneRepository = phoneRepository;
	}


	@GetMapping
	public Iterable<Phone> all() {
		return phoneRepository.findAll();
	}
}
