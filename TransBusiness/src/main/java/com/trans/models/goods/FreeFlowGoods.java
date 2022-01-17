package com.trans.models.goods;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

/*
 * Stone, Wood, Cereals, Sand, Gravel
 * 
 * Units ex: cubic meteres = m^3
 * 
 * Values ex: 10
 * 
 * => combined = 10 m^3
 */

@Entity
@DiscriminatorValue(value = "FreeFlow")
public class FreeFlowGoods extends GoodsEntity{
	
	@Getter
	@Setter
	private String unit;

	@Getter
	@Setter
	private double value;
	
	@Getter
	@Setter
	private double tonnage;
	
	public FreeFlowGoods(Double goodsSize, String string, Double i) {
		value = goodsSize.doubleValue();
		unit = string;
		tonnage = i;
	}
	
	public FreeFlowGoods() {
		
	}
	
	
}
