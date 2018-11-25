package com.spp.cp.db;

import com.spp.cp.domain.entities.Organization;
import org.springframework.data.repository.CrudRepository;

public interface OrganizationRepository extends CrudRepository<Organization, Long> {

}
