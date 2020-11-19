package com.azerconnect.phonenumberschecker.entity;

import java.util.ArrayList;
import java.util.List;

public class BlackList {
    private List<String> rangeMask;
    private List<String> wildcardMask;
    private List<String> exactMask;

    public BlackList() {
        rangeMask = new ArrayList<>();
        wildcardMask = new ArrayList<>();
        exactMask = new ArrayList<>();
    }

    public List<String> getRangeMask() {
        return rangeMask;
    }

    public void setRangeMask(List<String> rangeMask) {
        this.rangeMask = rangeMask;
    }

    public List<String> getWildcardMask() {
        return wildcardMask;
    }

    public void setWildcardMask(List<String> wildcardMask) {
        this.wildcardMask = wildcardMask;
    }

    public List<String> getExactMask() {
        return exactMask;
    }

    public void setExactMask(List<String> exactMask) {
        this.exactMask = exactMask;
    }

    @Override
    public String toString() {
        return "BlackList{" +
                "rangeMask=" + rangeMask +
                ", wildcardMask=" + wildcardMask +
                ", exactMask=" + exactMask +
                '}';
    }
}
