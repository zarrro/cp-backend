package com.spp.cp.db;

import com.spp.cp.domain.vehicle.Model;
import org.springframework.data.repository.CrudRepository;

public interface ModelRepository extends CrudRepository<Model, Long> {

}
