package com.azerconnect.phonenumberschecker.entity.response;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Response {
    private Map<String, String> response = new HashMap<>();
}
