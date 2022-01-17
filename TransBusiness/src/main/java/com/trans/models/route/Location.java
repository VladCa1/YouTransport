package com.trans.models.route;

import javax.persistence.CascadeType;
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
	private double latitude;

	@Getter
	private float longitude;

	@Getter
	private float altitude;

	@Getter
	@ManyToOne(cascade = CascadeType.ALL)
	private Route route;

	public Location(String country, String city, double latituted, float longitude, float altitude) {
		super();
		this.country = country;
		this.city = city;
		this.latitude = altitude;
		this.longitude = longitude;
		this.altitude = altitude;
	}

	public Location() {
		super();
	}

}
