package com.azerconnect.phonenumberschecker.entity.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

public class Request {
    private List<String> msisdnList;
    private String blacklistString;
    private String whitelistString;

    public List<String> getMsisdnList() {
        return msisdnList;
    }

    public String getBlacklistString() {
        return blacklistString;
    }

    public String getWhitelistString() {
        return whitelistString;
    }
}
