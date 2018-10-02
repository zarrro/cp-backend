package com.spp.cp.db;

import com.spp.cp.domain.Freight;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FreightRepository extends CrudRepository<Freight, Long> {

    List<Freight> findByOrderId(Long id);

}
