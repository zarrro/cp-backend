package com.spp.cp.domain.entities;

import javax.persistence.*;


@Embeddable
@Table(name = "itinerary_points")
public class ItineraryPoint {

    private int pointOrder;

    private Address address;

    /*
     * This is foreign key ID to itinerary, which is referenced by the Order entity.
     */
    public int getPointOrder() {
        return pointOrder;
    }

    public void setPointOrder(int pointOrder) {
        this.pointOrder = pointOrder;
    }

    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false)
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
