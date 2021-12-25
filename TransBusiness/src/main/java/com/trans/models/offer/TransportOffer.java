package com.trans.models.offer;



import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.trans.models.agent.FTPAgent;
import com.trans.models.transport.TransportEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue(value ="Transport")
public class TransportOffer extends Offer{

	@Getter
	@Setter
	@JoinColumn(name = "TO_AGENT")
	@ManyToOne
	private FTPAgent providerAgent;
	
	@Getter
	@Setter
	@Column(nullable = true)
	private String providerData;
	
	@Getter
	@Setter
	@OneToOne
	@JoinColumn(name = "TO_TEntity")
	private TransportEntity transportEntity;
	

}
