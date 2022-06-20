package com.trans.models.characteristic;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.trans.models.goods.GoodsEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue(value = "Goods")
public class GoodsEntityProperty extends Property{

	@Getter
	@Setter
	@ManyToOne
	private GoodsEntity goods;

	public GoodsEntityProperty() {
		super();
	}

	public GoodsEntityProperty(String details, Long id) { super(details, id); }
	
}
