package com.spp.cp.rest;

import com.spp.cp.domain.entities.Order;

import java.util.Date;

public class UpdateOrderParams {

    private Order.Type type;

    private String goods;

    private Date deadline;

    private Order.State state;

    public Order.Type getType() {
        return type;
    }

    public void setType(Order.Type type) {
        this.type = type;
    }

    public String getGoods() {
        return goods;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Order.State getState() {
        return state;
    }

    public void setState(Order.State state) {
        this.state = state;
    }
}
