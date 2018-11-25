package com.spp.cp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@ExcludeFromTest
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}