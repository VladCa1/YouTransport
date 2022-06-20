package com.trans.models.transport;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "BigVehicle")
public class BigVehicle extends TransportEntity{

}
