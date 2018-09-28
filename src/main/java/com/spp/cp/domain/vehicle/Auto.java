package com.spp.cp.domain.vehicle;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("auto")
public class Auto extends Vehicle {
}
