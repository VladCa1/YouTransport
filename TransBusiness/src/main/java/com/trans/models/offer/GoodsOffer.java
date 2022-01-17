package com.trans.models.offer;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.trans.models.agent.CAgent;
import com.trans.models.goods.GoodsEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue(value ="Goods")
public class GoodsOffer extends Offer{

	@Getter
	@Setter
	@JoinColumn(name = "GO_AGENT")
	@ManyToOne	
	private CAgent providerAgent;
	
	@Getter
	@Setter
	@Column(nullable = true)
	private String providerData;
	
	@Getter
	@Setter
	@OneToMany(cascade = CascadeType.ALL)
	private List<GoodsEntity> goods;
	
}
