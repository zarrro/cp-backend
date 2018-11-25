package com.spp.cp.domain;

import com.spp.cp.domain.entities.Freight;
import com.spp.cp.domain.entities.Order;
import com.spp.cp.rest.CreateFreightParams;

import java.util.List;

public class FreightManagerImpl extends EntityManagerBase implements FreightManager {
    @Override
    public List<Freight> createFreights(CreateFreightParams createParams) {

        return null;
    }

    private static Freight createFreightInstance(CreateFreightParams createParams, Order order) {
        //TODO: implement
        return null;
    }
}
