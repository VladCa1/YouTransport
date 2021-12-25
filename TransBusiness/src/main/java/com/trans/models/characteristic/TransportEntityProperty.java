package com.trans.models.characteristic;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.trans.models.transport.TransportEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue(value = "Transport")
public class TransportEntityProperty extends Property{
	
	@Getter
	@Setter
	@ManyToOne
	private TransportEntity transportEntity;
	
}
