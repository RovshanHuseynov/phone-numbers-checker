package com.azerconnect.phonenumberschecker.controller;

import com.azerconnect.phonenumberschecker.entity.request.Request;
import com.azerconnect.phonenumberschecker.entity.response.Response;
import com.azerconnect.phonenumberschecker.service.CheckService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CheckController.class)
class CheckControllerTest {
    @MockBean
    private CheckService checkService;

    @Test
    void isEligibleToSell(){
        Request request = new Request();
        request.setMsisdnList(Arrays.asList("994701234567","994702234568","994701234569","994771234569","994775123456"));
        request.setBlacklistString("701234567,702%,703999%,706999%,7070%,77555%,7012_4569");
        request.setWhitelistString("901234567,902%,903999%,906999%,9070%,97555%,9012_4569,771%");

        Map<String, String> response = new HashMap<>();
        response.put("994771234569", "OK");
        response.put("994701234569", "msisdn = 994701234569 is in blacklist");
        response.put("994702234568", "msisdn = 994702234568 is in blacklist");
        response.put("994701234567", "msisdn = 994701234567 is in blacklist");
        response.put("994775123456", "msisdn = 994775123456 is not in whitelist");

        doReturn(response).when(checkService).isEligibleToSell(request);
    }
}