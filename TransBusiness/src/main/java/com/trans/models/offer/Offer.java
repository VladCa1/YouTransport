package com.trans.models.offer;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;

import com.trans.models.pay.Pay;
import com.trans.models.route.Route;

import lombok.Getter;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Offer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Getter
	private Long id;
	
	@Getter
	@OneToOne
	private Route route;
	
	@Getter
	@OneToOne
	private Pay pay;
	
	@Getter
	private String description;
	
	@Getter
	@Setter
	@Column(nullable = false)
	private LocalDateTime fromDate;
	
	@Getter
	@Setter
	@Column(nullable = true)
	private LocalDateTime toDate;
	
	
}
