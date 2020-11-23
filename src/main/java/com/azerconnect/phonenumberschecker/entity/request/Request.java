package com.azerconnect.phonenumberschecker.entity.request;

import lombok.Data;

import java.util.List;

@Data
public class Request {
    private List<String> msisdnList;
    private String blacklistString;
    private String whitelistString;
}
