package com.trans.models.pay;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import com.trans.models.offer.Offer;

import lombok.Getter;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Pay {

	public static final String PER_DAY = "Per Day (/day)";
	public static final String PER_HOUR = "Per Hour (/hr)";
	public static final String PER_KM = "Per Kilometer (/km)";
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Getter
	private Long id;
	
	@Getter
	@OneToOne(cascade = CascadeType.ALL)
	private Offer offer;
	
	@Getter
	@Setter
	@Column(nullable = true)
	private String description;
	
	public Pay() {
		super();
	}
	
	@Transient
	public String getDiscriminatorValue(){
	    DiscriminatorValue val = this.getClass().getAnnotation( DiscriminatorValue.class );

	    return val == null ? null : val.value();
	}
	
}
