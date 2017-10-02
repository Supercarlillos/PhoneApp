package com.phone.app.controller;

import com.phone.app.domain.Phone;
import com.phone.app.domain.PurchaseOrder;
import com.phone.app.domain.exceptions.ValidationException;
import com.phone.app.repository.PurchaseOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/purchase_orders", produces = "application/json")
public class PurchaseOrderController {

	public static final String PHONES_END_POINT = "http://127.0.0.1:8080/phones";

	private final PurchaseOrderRepository purchaseOrderRepository;

	private final RestTemplate restTemplate;

	@Autowired
	public PurchaseOrderController(final PurchaseOrderRepository purchaseOrderRepository, final RestTemplate restTemplate) {
		this.purchaseOrderRepository = purchaseOrderRepository;
		this.restTemplate = restTemplate;
	}

	@GetMapping
	public Iterable<PurchaseOrder> all() {
		return purchaseOrderRepository.findAll();
	}

	@GetMapping
	@RequestMapping(value = "/{id}/total")
	public BigDecimal total(@PathVariable("id") Integer id) {
		final PurchaseOrder purchaseOrder = purchaseOrderRepository.findOne(id);
		return purchaseOrder != null ? purchaseOrder.total() : BigDecimal.ZERO;
	}

	@PostMapping
	public PurchaseOrder create(@RequestBody final PurchaseOrder purchaseOrder) throws ValidationException {
		purchaseOrder.validatePhones(getValidPhoneIds());
		final PurchaseOrder save = purchaseOrderRepository.save(purchaseOrder);
		log.info("PurchaseOrder: " + purchaseOrder + " created");

		return save;
	}

	private List<Integer> getValidPhoneIds() {
		//Extra Bonus Points:   the second endpoint use the first endpoint to validate the order.
		return Arrays.asList(restTemplate.getForObject(PHONES_END_POINT, Phone[].class)).stream()
				.map(phone -> phone.getId()).collect(Collectors.toList());
	}

}
