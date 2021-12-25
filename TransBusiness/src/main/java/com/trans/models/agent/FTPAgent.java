package com.trans.models.agent;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import com.trans.models.offer.TransportOffer;

import lombok.Getter;

@Entity
@DiscriminatorValue("FTPAgent")
public class FTPAgent extends Agent{

	@Getter
	@JoinColumn(name = "FTP_SOffers")
	@OneToMany
	private List<TransportOffer> selfOffers;
	
}
