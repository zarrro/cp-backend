package com.spp.cp.domain.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "freights")
public class Freight {

    public enum State {
        PENDING,
        CONFIRMED,
        LOADED,
        COMPLETED
    }

    private Long id;
    private State state;
    private Order order;
    private SubOrder subOrder;
    private Date loadDate;
    private List<Address> addresses;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "order_id")
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @ManyToOne
    @JoinColumn(name = "sub_order_id")
    public SubOrder getSubOrder() {
        return subOrder;
    }

    public void setSubOrder(SubOrder subOrder) {
        this.subOrder = subOrder;
    }

    public Date getLoadDate() {
        return loadDate;
    }

    public void setLoadDate(Date loadDate) {
        this.loadDate = loadDate;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    @ElementCollection
    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }
}
