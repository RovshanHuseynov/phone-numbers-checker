package com.azerconnect.phonenumberschecker.entity.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Request {
    private List<String> msisdnList;
    private String blacklistString;
    private String whitelistString;
}
