package com.azerconnect.phonenumberschecker.service;

import com.azerconnect.phonenumberschecker.entity.request.Request;
import com.azerconnect.phonenumberschecker.entity.response.Response;

import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
public class Service {
    public Response IsEligibleToSell(Request request){
        Response response = new Response();
        List<String> blackList = new ArrayList<>();

        String[] temp = request.getBlacklistString().split(",");
        for(String s : temp){
            //System.out.println(s);
            blackList.add(s);
        }

        List<String> whileList = new ArrayList<>();
        temp = request.getWhitelistString().split(",");
        for(String s : temp){
            whileList.add(s);
        }

        for(String s : request.getMsisdnList()){
            if(blackList.contains(s)){
                response.getResponse().put(s, "msisdn = " + s + " is in blacklist");
            }
            else if(whileList.size() > 0 && !whileList.contains(s)){
                response.getResponse().put(s, "msisdn = " + s + " is not in whilelist");
            }
            else{
                response.getResponse().put(s, "ok");
            }
        }

        return response;
    }
}
