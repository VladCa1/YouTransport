package com.trans.models.transport;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "SmallV")
public class SmallVehicle extends TransportEntity{
	
	
}
