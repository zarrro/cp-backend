package com.spp.cp.db;

import com.spp.cp.domain.entities.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {

    List<Order> findByOrgId(Long id);

    @Query("select o from Order o where o.org.id = ?#{ principal?.user.org.id }")
    Page<Order> findAllOrders(Pageable pageable);

    @Query("select o from Order o where o.org.id = ?#{ principal?.user.org.id }")
    List<Order> findAllOrders();

    @Query("select o from Order o where o.org.id = ?#{ principal?.user.org.id } and o.id = ?1")
    Optional<Order> findById(Long id);

}
