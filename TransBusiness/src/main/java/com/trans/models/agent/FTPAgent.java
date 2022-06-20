package com.trans.models.agent;

import java.util.List;

import javax.persistence.*;

import com.trans.models.offer.TransportOffer;

import com.trans.models.transport.Driver;
import lombok.Getter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@DiscriminatorValue("FTPAgent")
public class FTPAgent extends Agent{

	@Getter
	@JoinColumn(name = "FTP_SOffers")
	@OneToMany( cascade = CascadeType.ALL)
	private List<TransportOffer> selfOffers;
	
}
