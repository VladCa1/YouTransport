package com.trans.models.offer;

import java.time.LocalDate;

import javax.persistence.CascadeType;
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
	@Setter
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	private Route route;
	
	@Getter
	@Setter
	@OneToOne(cascade = CascadeType.ALL)
	private Pay pay;
	
	@Getter
	@Setter
	private String description;
	
	@Getter
	@Setter
	@Column(nullable = false)
	private LocalDate fromDate;
	
	@Getter
	@Setter
	@Column(nullable = true)
	private LocalDate toDate;
	
	
}
