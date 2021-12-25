package com.trans.models.transport;

import java.util.Date;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import com.trans.models.characteristic.TransportEntityProperty;
import com.trans.models.offer.TransportOffer;

import lombok.Getter;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class TransportEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Getter
	private Long id;
	
	@Getter
	private boolean assinged;
	
	@Getter
	private String make;
	
	@Getter
	private Date fabricationDate;
	
	@Getter
	@OneToOne
	private Driver driver;
	
	@Getter
	private String type;
	
	@Getter
	private int value;
	
	@Getter
	private String unit;
	
	@Getter
	private int tonnage;
	
	@Getter
	private boolean hazardousMaterialsAccepted;
	
	@Getter
	@OneToOne
	@JoinColumn(name = "TE_OFFER")
	private TransportOffer offer;
	
	public TransportEntity() {
		super();
	}
	
	@Getter
	@Setter
	@OneToMany
	private List<TransportEntityProperty> properties;
	
	
	@Transient
	public String getDiscriminatorValue(){
	    DiscriminatorValue val = this.getClass().getAnnotation( DiscriminatorValue.class );

	    return val == null ? null : val.value();
	}
	
}
