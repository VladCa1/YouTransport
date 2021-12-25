package com.trans.models.transport;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "BigV")
public class BigVehicle extends TransportEntity{

}
