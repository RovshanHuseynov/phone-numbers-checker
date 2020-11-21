package com.azerconnect.phonenumberschecker.entity.request;

import java.util.HashMap;
import java.util.Map;

public class InputList {
    private Map<String, Boolean> rangeMask;
    private Map<String, Boolean> wildcardMask;
    private Map<String, Boolean> exactMask;

    public InputList() {
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

    public Map<String, Boolean> getWildcardMask() {
        return wildcardMask;
    }

    public void setWildcardMask(Map<String, Boolean> wildcardMask) {
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
        return "InputList{" +
                "rangeMask=" + rangeMask +
                ", wildcardMask=" + wildcardMask +
                ", exactMask=" + exactMask +
                '}';
    }
}
