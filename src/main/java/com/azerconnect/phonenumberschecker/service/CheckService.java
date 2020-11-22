package com.azerconnect.phonenumberschecker.service;

import com.azerconnect.phonenumberschecker.entity.request.ParsedRequest;
import com.azerconnect.phonenumberschecker.entity.request.Request;
import com.azerconnect.phonenumberschecker.entity.response.Response;
import com.azerconnect.phonenumberschecker.exception.WrongJsonKey;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class CheckService {
    public Response IsEligibleToSell(Request request) {
        List<String> listMsisdn = request.getMsisdnList();
        String blacklistString = request.getBlacklistString();
        String whitelistString = request.getWhitelistString();
        ParsedRequest blackList = parseRequest(blacklistString);
        ParsedRequest whiteList = parseRequest(whitelistString);

        Response response = new Response();
        for(String currentPhoneNumber : listMsisdn){
            System.out.println(currentPhoneNumber + " is being checked");

            if(listContains(currentPhoneNumber, blackList, "blackList")){
                response.getResponse().put(currentPhoneNumber, "msisdn = " + currentPhoneNumber + " is in blacklist");
            }
            else if(listContains(currentPhoneNumber, whiteList, "whiteList")){
                response.getResponse().put(currentPhoneNumber, "ok");
            }
            else {
                System.out.println("not in whiteList");
                response.getResponse().put(currentPhoneNumber, "msisdn = " + currentPhoneNumber + " is not in whitelist");
            }
        }
        System.out.println("---------------------------------------");
        return response;
    }

    private ParsedRequest parseRequest(String listData){
        if(listData == null || listData.length() == 0){
            return null;
        }

        ParsedRequest parsedRequest = new ParsedRequest();
        String[] splitData = listData.split(",");

        int indexOfUnderline;
        for(String currentNumber : splitData){
            if(currentNumber.endsWith("%")){
                parsedRequest.getRangeMask().add(currentNumber.substring(0, currentNumber.length() - 1));
            }
            else if(currentNumber.contains("_")){
                indexOfUnderline = currentNumber.indexOf("_");
                parsedRequest.getWildcardMask().put(currentNumber.substring(0, indexOfUnderline), currentNumber.substring(indexOfUnderline + 1));
            }
            else {
                parsedRequest.getExactMask().add(currentNumber);
            }
        }

        return parsedRequest;
    }

    private boolean listContains(String currentPhoneNumber, ParsedRequest parsedRequest, String typeOfList) {
        if(typeOfList.equals("blackList") && parsedRequest == null){
            System.out.println("empty blackList");
            return false;
        }
        else if(typeOfList.equals("whiteList") && parsedRequest == null){
            System.out.println("empty whiteList");
            return true;
        }

        currentPhoneNumber = currentPhoneNumber.substring(3); // do not have to consider 994 while checking
        int lenOfCurrentPhoneNumber = currentPhoneNumber.length();
        Set<String> set = parsedRequest.getExactMask();

        if(set.contains(currentPhoneNumber)){
            System.out.println(typeOfList + " exact: true");
            return true;
        }

        set = parsedRequest.getRangeMask();
        for(int i=1; i<lenOfCurrentPhoneNumber; i++){
            if(set.contains(currentPhoneNumber.substring(0, i))){
                System.out.println(typeOfList + " range: " + true + " " + currentPhoneNumber.substring(0, i));
                return true;
            }
        }

        Map<String, String> map = parsedRequest.getWildcardMask();
        String searchedKey, searchedValue;
        for(int i=0; i<lenOfCurrentPhoneNumber; i++){
            searchedKey = currentPhoneNumber.substring(0,i);
            searchedValue = currentPhoneNumber.substring(i+1);
            if(map.containsKey(searchedKey) && map.get(searchedKey).equals(searchedValue)){
                System.out.println(typeOfList + " wildcard: true " + searchedKey + "_" + searchedValue);
                return true;
            }
        }

        return false;
    }
}
