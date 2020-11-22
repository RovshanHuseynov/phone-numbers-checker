package com.azerconnect.phonenumberschecker.controller;

import com.azerconnect.phonenumberschecker.entity.request.Request;
import com.azerconnect.phonenumberschecker.entity.response.Response;
import com.azerconnect.phonenumberschecker.service.CheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/masklist")
public class CheckController {
    private final CheckService checkService;

    public CheckController(@Autowired CheckService checkService) {
        this.checkService = checkService;
    }

    @PostMapping("/iseligible")
    public Response IsEligibleToSell(@RequestBody Request request){
        return checkService.IsEligibleToSell2(request);
    }
}
