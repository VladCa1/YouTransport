package com.trans.models.route;

import java.util.List;

import javax.persistence.CascadeType;
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
	@OneToOne(orphanRemoval=false)
	private Location from;

	@Getter
	@OneToOne(orphanRemoval=false)
	private Location to;

	@Getter
	@OneToMany(orphanRemoval=false)
	private List<Location> intermediary;

	@Getter
	@Setter
	@OneToOne(cascade = CascadeType.ALL)
	private Offer offer;

	@Getter
	@Setter
	private String duration;

	@Getter
	@Setter
	private String distance;

	public Route(Location from, Location to, List<Location> intermediary) {
		super();
		this.from = from;
		this.to = to;
		this.intermediary = intermediary;
	}

	public Route(Location from, Location to) {
		super();
		this.from = from;
		this.to = to;
	}

	public Route() {
		super();
	}

	public Route(Location from) {
		super();
		this.from = from;
		this.to = new Location(Constants.NOT_SPECIFIED, Constants.NOT_SPECIFIED, 0,
				0, 0);

	}

	public Route(Location to, Location from, String distance, String duration) {
		super();
		this.from = from;
		this.to = to;
		this.distance = distance;
		this.duration = duration;
	}
}
