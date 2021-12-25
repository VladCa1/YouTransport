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
	private int value;
	
	@Getter
	@Setter
	private int tonnage;
	
}
