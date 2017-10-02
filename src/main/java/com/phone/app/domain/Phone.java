package com.phone.app.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Phone {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private String name;

	private String description;

	private BigDecimal price;

	private URL image;

	public static Phone.PhoneBuilder builder() {
		return new Phone.PhoneBuilder();
	}

	public static class PhoneBuilder {
		private Integer id;

		private String name;

		private String description;

		private BigDecimal price;

		private URL image;

		PhoneBuilder() {
		}

		public Phone.PhoneBuilder id(Integer id) {
			this.id = id;
			return this;
		}

		public Phone.PhoneBuilder name(String name) {
			this.name = name;
			return this;
		}

		public Phone.PhoneBuilder description(String description) {
			this.description = description;
			return this;
		}

		public Phone.PhoneBuilder price(BigDecimal price) {
			this.price = price.setScale(2,BigDecimal.ROUND_CEILING);
			return this;
		}

		public Phone.PhoneBuilder image(String image) throws MalformedURLException {
			this.image = new URL(image);
			return this;
		}

		public Phone build() {
			return new Phone(this.id, this.name, this.description, this.price, this.image);
		}

	}

}