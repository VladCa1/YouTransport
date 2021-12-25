package com.trans.models.goods;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

/*
 * Units are normaly represented in square meteres as it represents the space of the truck, etc
 * 
 *  canBeStackes so the transport provider knows if you can stack 2 pallets on top of each other
 */


@Entity
@DiscriminatorValue(value = "Pallet")
public class PalletGoods extends GoodsEntity{

	@Getter
	@Setter
	private String unit;
	
	@Getter
	@Setter
	private String value;
	
	@Getter
	@Setter
	private int tonnage;
	
	@Getter
	@Setter
	private boolean canBeStacked;
	
}
