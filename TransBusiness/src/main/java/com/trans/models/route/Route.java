package com.trans.models.route;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.trans.models.offer.Offer;
import com.trans.utils.Constants;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Route {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Getter
	private Long id;
	
	@Getter
	@OneToOne
	private Location from;
	
	@Getter
	@OneToOne
	private Location to;
	
	@Getter
	@OneToMany
	private List<Location> intermediary;
	
	@Getter
	@Setter
	@OneToOne
	private Offer offer;

	public Route(Location from, Location to, List<Location> intermediary) {
		super();
		this.from = from;
		this.to = to;
		this.intermediary = intermediary;
	}

	public Route() {
		super();
	}

	public Route(Location from) {
		super();
		this.from = from;
		this.to = new Location(Constants.NOT_SPECIFIED, Constants.NOT_SPECIFIED);
		
	}
	
	
	
}
