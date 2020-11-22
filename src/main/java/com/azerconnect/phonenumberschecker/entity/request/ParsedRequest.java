package com.azerconnect.phonenumberschecker.entity.request;

import java.util.HashMap;
import java.util.Map;

public class ParsedRequest {
    private Map<String, Boolean> rangeMask;
    private Map<String, String> wildcardMask;
    private Map<String, Boolean> exactMask;

    public ParsedRequest() {
        rangeMask = new HashMap<>();
        wildcardMask = new HashMap<>();
        exactMask = new HashMap<>();
    }

    public Map<String, Boolean> getRangeMask() {
        return rangeMask;
    }

    public void setRangeMask(Map<String, Boolean> rangeMask) {
        this.rangeMask = rangeMask;
    }

    public Map<String, String> getWildcardMask() {
        return wildcardMask;
    }

    public void setWildcardMask(Map<String, String> wildcardMask) {
        this.wildcardMask = wildcardMask;
    }

    public Map<String, Boolean> getExactMask() {
        return exactMask;
    }

    public void setExactMask(Map<String, Boolean> exactMask) {
        this.exactMask = exactMask;
    }

    @Override
    public String toString() {
        return "ParsedRequest{" +
                "rangeMask=" + rangeMask +
                ", wildcardMask=" + wildcardMask +
                ", exactMask=" + exactMask +
                '}';
    }
}