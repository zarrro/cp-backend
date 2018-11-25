package com.spp.cp.db;

import com.spp.cp.domain.entities.Company;
import org.springframework.data.repository.CrudRepository;

public interface CompanyRepository extends CrudRepository<Company, Long> {

}
