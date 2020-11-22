package com.azerconnect.phonenumberschecker.entity.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response {
    Map<String, String> response = new HashMap<>();
}
