package com.spp.cp.db;

import com.spp.cp.domain.entities.Address;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, Long> {
}
