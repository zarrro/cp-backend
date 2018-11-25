package com.spp.cp.db;

import com.spp.cp.domain.entities.Itinerary;
import org.springframework.data.repository.CrudRepository;

public interface ItineraryRepository extends CrudRepository<Itinerary, Long> {
}
