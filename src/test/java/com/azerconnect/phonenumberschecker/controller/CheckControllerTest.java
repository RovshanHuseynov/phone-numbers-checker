package com.azerconnect.phonenumberschecker.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CheckController.class)
class CheckControllerTest {
    private final String URL = "http://localhost:8080/api/masklist/iseligible";

    @BeforeEach
    void setUp() { }

    @AfterEach
    void tearDown() { }

    @Autowired
    private MockMvc mockMvc;

    @Test
    void isEligibleToSell() throws Exception {
        //RequestBuilder request = post(URL);
        //MvcResult result = mockMvc.perform(request).andReturn();
        //assertEquals("", result.getResponse().getContentAsString());

        //Mockito.when(checkController.IsEligibleToSell(null));

        String stringRequest = "{\n" +
                "    \"msisdnList\": [\n" +
                "        \"994701234567\",\"994702234568\",\"994701234569\",\"994771234569\",\"994775123456\"\n" +
                "    ],\n" +
                "    \"blacklistString\": \"701234567,702%,703999%,706999%,7070%,77555%,7012_4569\",\n" +
                "    \"whitelistString\": \"901234567,902%,903999%,906999%,9070%,97555%,9012_4569,771%\"\n" +
                "}";

        String stringResponse = "{\n" +
                "    \"994771234569\": \"OK\",\n" +
                "    \"994701234569\": \"msisdn = 994701234569 is in blacklist\",\n" +
                "    \"994702234568\": \"msisdn = 994702234568 is in blacklist\",\n" +
                "    \"994701234567\": \"msisdn = 994701234567 is in blacklist\",\n" +
                "    \"994775123456\": \"msisdn = 994775123456 is not in whitelist\"\n" +
                "}";

        String stringResponse1 = "{}";

        mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(stringRequest))
                .andExpect(status().isOk())
                .andExpect(content().string(stringResponse));
    }
}