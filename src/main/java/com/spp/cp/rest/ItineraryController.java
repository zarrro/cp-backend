package com.spp.cp.rest;


import com.spp.cp.db.ItineraryRepository;
import com.spp.cp.domain.entities.Itinerary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/itineraries")
public class ItineraryController {

    @Autowired
    private ItineraryRepository itineraryRepo;

    @GetMapping
    public Iterable<Itinerary> itineraries() {
        return itineraryRepo.findAll();
    }
}
