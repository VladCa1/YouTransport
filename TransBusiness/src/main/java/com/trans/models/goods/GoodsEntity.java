package com.trans.models.goods;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.trans.models.characteristic.GoodsEntityProperty;
import com.trans.models.offer.GoodsOffer;

import lombok.Getter;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class GoodsEntity {
	
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
}
