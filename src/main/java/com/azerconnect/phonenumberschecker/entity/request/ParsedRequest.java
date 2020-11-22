package com.azerconnect.phonenumberschecker.entity.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
public class ParsedRequest {
    private final Map<String, Boolean> rangeMask = new HashMap<>();
    private final Map<String, String> wildcardMask = new HashMap<>();
    private final Map<String, Boolean> exactMask = new HashMap<>();
}
