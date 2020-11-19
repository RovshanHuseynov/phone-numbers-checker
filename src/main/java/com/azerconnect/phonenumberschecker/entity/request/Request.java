package com.azerconnect.phonenumberschecker.entity.request;

import java.util.ArrayList;
import java.util.List;

public class Request {
    private List<String> msisdnList;
    private String blacklistString;
    private String whitelistString;

    public Request() {
        msisdnList = new ArrayList<>();
        blacklistString = "";
        whitelistString = "";
    }

    public List<String> getMsisdnList() {
        return msisdnList;
    }

    public void setMsisdnList(List<String> msisdnList) {
        this.msisdnList = msisdnList;
    }

    public String getBlacklistString() {
        return blacklistString;
    }

    public void setBlacklistString(String blacklistString) {
        this.blacklistString = blacklistString;
    }

    public String getWhitelistString() {
        return whitelistString;
    }

    public void setWhitelistString(String whitelistString) {
        this.whitelistString = whitelistString;
    }

    @Override
    public String toString() {
        return "Request{" +
                "msisdnList=" + msisdnList +
                ", blacklistString='" + blacklistString + '\'' +
                ", whitelistString='" + whitelistString + '\'' +
                '}';
    }
}
