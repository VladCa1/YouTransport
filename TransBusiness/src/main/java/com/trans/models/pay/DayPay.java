package com.trans.models.pay;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.trans.utils.Constants;

import lombok.Getter;

@Entity
@DiscriminatorValue("Day")
public class DayPay extends Pay{

	@Getter
	private double rate;
	
	@Getter
	private String currency;
	
	@Getter
	private int period;

	public DayPay(Double rate, String currency, int period) {
		super();
		this.rate = rate;
		this.currency = currency;
		this.period = period;
	}

	public DayPay(Double rate, String currency) {
		super();
		this.rate = rate;
		this.currency = currency;
		this.period = 1;
	}
	
	public DayPay(Double rate) {
		super();
		this.rate = rate;
		this.currency = Constants.EURO;
		this.period = 1;
	}
	
	public DayPay() {
		super();
	}
	
}
