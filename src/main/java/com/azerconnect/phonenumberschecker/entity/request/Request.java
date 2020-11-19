package com.azerconnect.phonenumberschecker.entity.request;

import java.util.Arrays;

public class Request {
    private String[] msisdnList;
    private String blacklistString;
    private String whitelistString;

    public Request() {
    }

    public Request(String[] msisdnList, String blacklistString, String whitelistString) {
        this.msisdnList = msisdnList;
        this.blacklistString = blacklistString;
        this.whitelistString = whitelistString;
    }

    public String[] getMsisdnList() {
        return msisdnList;
    }

    public void setMsisdnList(String[] msisdnList) {
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
                "msisdnList=" + Arrays.toString(msisdnList) +
                ", blacklistString='" + blacklistString + '\'' +
                ", whitelistString='" + whitelistString + '\'' +
                '}';
    }
}
