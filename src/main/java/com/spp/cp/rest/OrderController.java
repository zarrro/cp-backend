package com.spp.cp.rest;

import com.spp.cp.db.OrderRepository;
import com.spp.cp.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepo;

    @PostMapping(consumes = "application/json")
    public Order createOrder(@RequestBody Order order) {
        if (order == null) {
            throw new IllegalArgumentException("order is null");
        }

        return this.orderRepo.save(order);
    }

    @GetMapping
    public List<Order> getOrdersInfo() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        System.out.println(auth.getName());

//        Iterable<Order> orders = this.orderRepo.findAllCurrentOrgOrders();
//        List<Order> result = new LinkedList<>();
//        orders.forEach(order -> result.add(order));
        return this.orderRepo.findAllCurrentOrgOrders();
    }

    @PatchMapping
    public void updateOrder(@RequestBody Order order) {
        if (order == null) {
            throw new IllegalArgumentException("order id is not set");
        }
        if (!this.orderRepo.existsById(order.getId())) {
            throw new IllegalArgumentException("order with id " + order.getId() + " doesn't exist");
        }
        this.orderRepo.save(order);
    }
}
