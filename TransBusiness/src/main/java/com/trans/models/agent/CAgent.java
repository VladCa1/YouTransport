package com.trans.models.agent;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import com.trans.models.offer.GoodsOffer;

import lombok.Getter;

@Entity
@DiscriminatorValue("CAgent")
public class CAgent extends Agent{

	@Getter
	@JoinColumn(name = "C_SOffers")
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<GoodsOffer> selfOffers;
	
}
