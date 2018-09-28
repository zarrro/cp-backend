package com.spp.cp;

import com.spp.cp.db.OrderRepository;
import com.spp.cp.domain.Company;
import com.spp.cp.domain.Order;
import com.spp.cp.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * Initialize the database with testData.
 */
@Component
@ExcludeFromTest
public class DataInit implements ApplicationRunner {

    @Autowired
    private OrderRepository orderRepo;

    @Override
    public void run(ApplicationArguments args) {

    }
}