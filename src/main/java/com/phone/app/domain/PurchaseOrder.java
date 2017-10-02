package com.phone.app.domain;

import com.google.common.collect.Lists;
import com.phone.app.domain.exceptions.ValidationException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class PurchaseOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@NotEmpty
	private String name;

	@NotEmpty
	private String surname;

	@NotEmpty
	@Email
	private String email;

	@NotEmpty
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "order_phone",
			joinColumns = @JoinColumn(name = "order_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "phone_id", referencedColumnName = "id"))
	private List<Phone> phones = Lists.newArrayList();

	public void validatePhones(final List<Integer> validPhonesIds) throws ValidationException {
		if (validPhonesIds
				.containsAll(phones.stream().map(phone -> phone.getId()).collect(Collectors.toList()))) {
			return;
		}

		throw new ValidationException(
				Lists.newArrayList(Error.builder().field("id").description("Invalid phone ids").build()));

	}

	public BigDecimal total() {
		return phones.stream().map(phone -> phone.getPrice()).reduce((price1, price2) -> price1.add(price2))
				.orElse(BigDecimal.ZERO);

	}

	public static PurchaseOrderBuilder builder() {
		return new PurchaseOrderBuilder();
	}

	public static class PurchaseOrderBuilder {
		private Integer id;

		private String name;

		private String surname;

		private String email;

		private List<Phone> phones;

		public PurchaseOrderBuilder id(Integer id) {
			this.id = id;
			return this;
		}

		public PurchaseOrderBuilder name(String name) {
			this.name = name;
			return this;
		}

		public PurchaseOrderBuilder surname(String surname) {
			this.surname = surname;
			return this;
		}

		public PurchaseOrderBuilder email(String email) {
			this.email = email;
			return this;
		}

		public PurchaseOrderBuilder phones(List<Phone> phones) {
			this.phones = phones;
			return this;
		}

		public PurchaseOrder build() {
			return new PurchaseOrder(this.id, this.name, this.surname, this.email, this.phones);
		}

	}

}