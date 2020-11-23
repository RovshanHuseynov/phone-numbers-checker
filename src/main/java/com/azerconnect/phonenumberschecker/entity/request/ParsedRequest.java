package com.azerconnect.phonenumberschecker.entity.request;

import lombok.Data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Data
public class ParsedRequest {
    private final Set<String> rangeMask = new HashSet<>();
    private final Map<String, String> wildcardMask = new HashMap<>();
    private final Set<String>  exactMask = new HashSet<>();
}
