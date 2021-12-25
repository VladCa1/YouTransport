package com.trans.models.pay;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.trans.utils.Constants;

import lombok.Getter;

@Entity
@DiscriminatorValue("Day")
public class DayPay extends Pay{

	@Getter
	private int rate;
	
	@Getter
	private String currency;
	
	@Getter
	private int period;

	public DayPay(int rate, String currency, int period) {
		super();
		this.rate = rate;
		this.currency = currency;
		this.period = period;
	}

	public DayPay(int rate, String currency) {
		super();
		this.rate = rate;
		this.currency = currency;
		this.period = 1;
	}
	
	public DayPay(int rate) {
		super();
		this.rate = rate;
		this.currency = Constants.EURO;
		this.period = 1;
	}
	
	public DayPay() {
		super();
	}
	
}
