package com.trans.models.goods;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

/*
 * Separated from Free Flow because 
 * it needs a special type of tank to transport
 */


@Entity
@DiscriminatorValue(value = "Liquid")
public class LiquidGoods extends GoodsEntity{

	@Getter
	@Setter
	private String unit;
	
	@Getter
	@Setter
	private double value;
	
	@Getter
	@Setter
	private double tonnage;
	
	
	public LiquidGoods(Double goodsSize, String string, Double i) {
		value = goodsSize.doubleValue();
		unit = string;
		tonnage = i;
	}
	
	public LiquidGoods() {
		// TODO Auto-generated constructor stub
	}
}
