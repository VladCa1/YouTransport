package com.trans.models.offer;



import javax.persistence.*;

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
	@ManyToOne( cascade = CascadeType.ALL)
	private FTPAgent providerAgent;
	
	@Getter
	@Setter
	@Column(nullable = true)
	private String providerData;

	@Getter
	@Setter
	private int maxDistanceFromSource;
	
	@Getter
	@Setter
	@OneToOne( cascade = CascadeType.ALL)
	@JoinColumn(name = "TO_TEntity")
	private TransportEntity transportEntity;
	

}
