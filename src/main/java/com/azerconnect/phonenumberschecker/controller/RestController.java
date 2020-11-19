package com.azerconnect.phonenumberschecker.controller;

import com.azerconnect.phonenumberschecker.entity.request.Request;
import com.azerconnect.phonenumberschecker.entity.response.Response;
import com.azerconnect.phonenumberschecker.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("api/masklist")
public class RestController {
    private final Service service;

    public RestController(@Autowired Service service) {
        this.service = service;
    }

    @PostMapping("/iseligible")
    public Response IsEligibleToSell(@RequestBody Request request){
        return service.IsEligibleToSell(request);
    }
}
