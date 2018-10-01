package com.spp.cp.db;

import com.spp.cp.domain.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {

    List<Order> findByOrgId(Long id);

}
