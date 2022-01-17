package com.trans.models.pay;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.trans.utils.Constants;

import lombok.Getter;

@Entity
@DiscriminatorValue("Distance")
public class DistancePay extends Pay{
	
	@Getter
	@Column(nullable = false)
	private double rate;
	
	@Getter
	@Column(nullable = false)
	private String currency;
	
	@Getter
	@Column(nullable = false)
	private String distanceUnit;

	public DistancePay(double rate, String currency, String distanceUnit) {
		super();
		this.rate = rate;
		this.currency = currency;
		this.distanceUnit = distanceUnit;
	}

	public DistancePay(double rate, String currency) {
		super();
		this.rate = rate;
		this.currency = currency;
		this.distanceUnit = Constants.KM;
	}

	public DistancePay(double rate) {
		super();
		this.rate = rate;
		this.currency = Constants.EURO;
		this.distanceUnit = Constants.KM;
	}
	
	public DistancePay() {
		super();
	}
}
