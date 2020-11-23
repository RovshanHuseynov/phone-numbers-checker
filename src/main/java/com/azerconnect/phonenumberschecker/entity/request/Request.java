package com.azerconnect.phonenumberschecker.entity.request;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Request {
    private List<String> msisdnList = new ArrayList<>();
    private String blacklistString = "";
    private String whitelistString = "";
}
