package com.spp.cp.config;

import com.spp.cp.domain.OrderService;
import com.spp.cp.domain.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainConfig {

    @Bean
    public OrderService orderAndFreightManager() {
        return new OrderServiceImpl();
    }
}
