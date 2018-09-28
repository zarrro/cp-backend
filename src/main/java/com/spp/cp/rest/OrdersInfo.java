package com.spp.cp.rest;

import com.spp.cp.domain.Order;

import java.util.List;

public class OrdersInfo {

    private List<Order> orders;

    public List<Order> getOrders() {
        return orders;
    }
    
    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
