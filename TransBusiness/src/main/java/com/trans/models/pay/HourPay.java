package com.trans.models.pay;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.trans.utils.Constants;

import lombok.Getter;

@Entity
@DiscriminatorValue("Hour")
public class HourPay extends Pay{

	@Getter
	@Column(nullable = false)
	private int rate;
	
	@Getter
	@Column(nullable = false)
	private String currency;
	
	@Getter
	@Column(nullable = false)
	private int period;

	public HourPay(int rate, String currency, int period) {
		super();
		this.rate = rate;
		this.currency = currency;
		this.period = period;
	}

	public HourPay(int rate, String currency) {
		super();
		this.rate = rate;
		this.currency = currency;
		this.period = 1;
	}
	
	public HourPay(int rate) {
		super();
		this.rate = rate;
		this.currency = Constants.EURO;
		this.period = 1;
	}


	public HourPay() {
		super();
	}

	
	
}
