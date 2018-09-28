package com.spp.cp.domain.vehicle;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@DiscriminatorValue("trailor")
public class Trailor extends Vehicle {

}
