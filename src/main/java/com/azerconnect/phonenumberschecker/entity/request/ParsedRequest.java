package com.azerconnect.phonenumberschecker.entity.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ParsedRequest {
    private final Set<String> rangeMask = new HashSet<>();
    private final Map<String, String> wildcardMask = new HashMap<>();
    private final Set<String>  exactMask = new HashSet<>();

    public Set<String> getRangeMask() {
        return rangeMask;
    }

    public Map<String, String> getWildcardMask() {
        return wildcardMask;
    }

    public Set<String> getExactMask() {
        return exactMask;
    }
}
