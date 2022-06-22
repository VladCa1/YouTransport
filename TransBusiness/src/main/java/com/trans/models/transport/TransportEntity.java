package com.trans.models.transport;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.trans.utils.StringListConverter;
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
	@Setter
	private String make;
	
	@Getter
	@Setter
	private Date fabricationDate;
	
	@Getter
	@Setter
	private Long driverId;
	
	@Getter
	@Setter
	private String type;
	
	@Getter
	@Setter
	private int horsePower;
	
	@Getter
	@Setter
	private double tonnage;
	
	@Getter
	@Setter
	private boolean hazardousMaterialsAccepted;

	@Getter
	@Setter
	@Convert(converter = StringListConverter.class)
	private List<String> acceptedGoods;
	
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
