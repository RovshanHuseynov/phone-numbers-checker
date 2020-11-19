package com.azerconnect.phonenumberschecker.controller;

import com.azerconnect.phonenumberschecker.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("api/masklist")
public class RestController {
    private final Service service;

    public RestController(@Autowired Service service) {
        this.service = service;
    }

    @RequestMapping("/iseligible")
    public void IsEligibleToSell(){
        System.out.println("hello");
        System.out.println(service.IsEligibleToSell("hello2"));
    }
}
