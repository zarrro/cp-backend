package com.spp.cp.rest;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController()
public class BaseController {

    @RequestMapping("/user")
    public Principal user(Principal user) {
        return user;
    }
}