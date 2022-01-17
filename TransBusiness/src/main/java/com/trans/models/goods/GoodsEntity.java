package com.trans.models.goods;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.trans.models.characteristic.GoodsEntityProperty;
import com.trans.models.offer.GoodsOffer;

import lombok.Getter;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class GoodsEntity {
	
	public static final String LIQUID_GOODS  = "Liquid (/l)";
	public static final String FREE_FLOW_GOODS = "Free flow (/t)";
	public static final String PALLET_GOODS = "Pallets (/number)";
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Getter
	private Long id;
	
	@Getter
	@ManyToOne
	private GoodsOffer offer;
	
	@Getter
	@Setter
	@OneToMany
	private List<GoodsEntityProperty> properties;

	@Transient
	public String getDiscriminatorValue(){
	    DiscriminatorValue val = this.getClass().getAnnotation( DiscriminatorValue.class );

	    return val == null ? null : val.value();
	}
}
