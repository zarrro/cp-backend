package com.spp.cp.rest;

import com.spp.cp.db.OrderRepository;
import com.spp.cp.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

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
    public OrdersInfo getOrdersInfo() {
        Iterable<Order> orders = this.orderRepo.findAll();
        OrdersInfo result = new OrdersInfo();
        result.setOrders(new ArrayList<>());
        orders.forEach(order -> {
            result.getOrders().add(order);
        });
        return result;
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
