package com.spp.cp.domain;

import com.spp.cp.domain.entities.Freight;
import com.spp.cp.rest.CreateFreightParams;

import java.util.List;

public interface FreightManager {

    /**
     * Create freights.
     */
    List<Freight> createFreights(CreateFreightParams createParams);

}
