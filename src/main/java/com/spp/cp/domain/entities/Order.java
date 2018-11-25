package com.spp.cp.domain.entities;

import com.spp.cp.domain.Exceptions;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order extends Auditable implements Serializable {

    private Long id;
    private Company customer;
    private State state;
    private Company contractor;
    private String goods;
    private Type type;
    private Date deadline;
    private Itinerary itenary;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "customer_company_id", nullable = false)
    public Company getCustomer() {
        return customer;
    }

    public void setCustomer(Company customer) {
        this.customer = customer;
    }

    @ManyToOne
    @JoinColumn(name = "contractor_company_id", nullable = false)
    public Company getContractor() {
        return contractor;
    }

    public void setContractor(Company contractor) {
        this.contractor = contractor;
    }

    public String getGoods() {
        return goods;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    /**
     * Optional. The date by which all freight will have to be completed. UserEntity will get warning,
     * if the date is closing and there is not completed freights
     *
     * @return
     */
    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    @ManyToOne
    @JoinColumn(name = "itin_id", nullable = false)
    public Itinerary getItenary() {
        return itenary;
    }

    public void setItenary(Itinerary itenary) {
        this.itenary = itenary;
    }

    public enum State {
        OPEN,
        CLOSED;

        public boolean isValidTransition(State nextState) {
            switch (this) {
                case OPEN: {
                    switch (nextState) {
                        case OPEN:
                        case CLOSED: {
                            return true;
                        }
                        default:
                            return false;
                    }
                }
                case CLOSED: {
                    switch (nextState) {
                        case CLOSED: {
                            return true;
                        }
                        default: {
                            return false;
                        }
                    }
                }
                default:
                    //case needs to be added for each new state in the enum
                    throw new IllegalStateException("unknown state " + this);
            }
        }
    }

    public enum Type {
        FTL, LTL
    }
}
