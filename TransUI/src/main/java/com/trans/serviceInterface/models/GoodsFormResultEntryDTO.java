package com.trans.serviceInterface.models;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

public class GoodsFormResultEntryDTO {
	
	@Getter
	@Setter
	String id;
	
	@Getter
	@Setter
	String toLocationCountry;
	
	@Getter
	@Setter
	String toLocationCity;
	
	@Getter
	@Setter
	String fromLocationCountry;
	
	@Getter
	@Setter
	String fromLocationCity;
	
	@Getter
	@Setter
	String goodsType;
	
	@Getter
	@Setter
	Double goodsSize;
	
	@Getter
	@Setter
	Double goodsPalletSize;
	
	@Getter
	@Setter
	String characteristicOne;
	
	@Getter
	@Setter
	String characteristicTwo;
	
	@Getter
	@Setter
	String payType;
	
	@Getter
	@Setter
	Double payValue;
	
	@Getter
	@Setter
	String description;
	
	@Getter
	@Setter
	LocalDate toDate;
	
	@Getter
	@Setter
	LocalDate fromDate;
	
}
