package com.trans.models.route;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Getter;

@Entity
public class Location {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Getter
	private Long id;
	
	@Getter
	private String country;
	
	@Getter
	private String city;
	
	@Getter
	@ManyToOne
	private Route route;

	public Location(String country, String city) {
		super();
		this.country = country;
		this.city = city;
	}
	
	public Location() {
		super();
	}
	
}
