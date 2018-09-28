package com.spp.cp.domain;

import javax.persistence.*;

@Entity
@Table(name = "sub_orders")
public class SubOrder {

    private Long id;

    private Order orderId;

    private Company contractor;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name="order_id", nullable = false, updatable = false)
    public Order getOrderId() {
        return orderId;
    }

    public void setOrderId(Order orderId) {
        this.orderId = orderId;
    }

    @ManyToOne
    @JoinColumn(name="contractor_id")
    public Company getContractor() {
        return contractor;
    }

    public void setContractor(Company contractor) {
        this.contractor = contractor;
    }
}
