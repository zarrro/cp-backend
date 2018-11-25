package com.spp.cp.db;

import com.spp.cp.domain.entities.City;
import org.springframework.data.repository.CrudRepository;

public interface CityRepository extends CrudRepository<City, Long> {
}
