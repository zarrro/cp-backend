package com.spp.cp.domain.entities;

import javax.persistence.*;
import java.util.List;

@Entity
public class Itinerary {

    private Long id;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private List<ItineraryPoint> points;

    @ElementCollection(targetClass = ItineraryPoint.class)
    @CollectionTable(name = "itinerary_points", joinColumns = @JoinColumn(name = "itinerary_id",
            referencedColumnName="id"))
    @OrderBy("pointOrder ASC")
    public List<ItineraryPoint> getPoints() {
        return points;
    }

    public void setPoints(List<ItineraryPoint> points) {
        this.points = points;
    }
}
