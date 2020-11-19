package com.azerconnect.phonenumberschecker.entity.response;

import java.util.HashMap;
import java.util.Map;

public class Response {
    Map<String, String> response = new HashMap<>();

    public Response() {
    }

    public Response(Map<String, String> response) {
        this.response = response;
    }

    public Map<String, String> getResponse() {
        return response;
    }

    public void setResponse(Map<String, String> response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "Response{" +
                "response=" + response +
                '}';
    }
}
