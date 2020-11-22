package com.azerconnect.phonenumberschecker.service;

import com.azerconnect.phonenumberschecker.entity.request.ParsedRequest;
import com.azerconnect.phonenumberschecker.entity.request.Request;
import com.azerconnect.phonenumberschecker.entity.response.Response;
import com.azerconnect.phonenumberschecker.exception.IllegalCharacterException;
import com.azerconnect.phonenumberschecker.exception.WrongLengthException;

import java.util.Map;

@org.springframework.stereotype.Service
public class CheckService {
    private ParsedRequest blackList;
    private ParsedRequest whiteList;

    public Response IsEligibleToSell(Request request){
        Response response = new Response();
        blackList = null;
        whiteList = null;

        parseRequest("blacklistString", request.getBlacklistString());
        parseRequest("whitelistString", request.getWhitelistString());

        for(String currentPhoneNumber : request.getMsisdnList()){
            System.out.println(currentPhoneNumber + " is being checked");

            if(currentPhoneNumber.length() != 12){
                throw new WrongLengthException("msisdnList contains " + currentPhoneNumber + " which its length is not 12");
            }
            else if(!isDigit(currentPhoneNumber)){
                throw new IllegalCharacterException("msisdnList contains " + currentPhoneNumber + " which includes illegal character");
            }

            if(mapContains(currentPhoneNumber, blackList, "blackList")){
                response.getResponse().put(currentPhoneNumber, "msisdn = " + currentPhoneNumber + " is in blacklist");
            }
            else if(mapContains(currentPhoneNumber, whiteList, "whiteList")){
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

    private void parseRequest(String listName, String inputData){
        if(inputData.length() == 0){
            return;
        }

        String[] splitData = inputData.split(",");

        switch (listName){
            case "blacklistString" :
                blackList = new ParsedRequest();
                fillMap(listName, blackList, splitData);
                break;
            case "whitelistString" :
                whiteList = new ParsedRequest();
                fillMap(listName, whiteList, splitData);
                break;
            default:
                System.out.println("Exception");
        }
    }

    private void fillMap(String mapName, ParsedRequest map, String[] splitData) {
        int indexOfUnderline;

        for(String currentNumber : splitData){
            if(currentNumber.endsWith("%")){
                if(!isDigit(currentNumber.substring(0, currentNumber.length() - 1))){
                    throw new IllegalCharacterException(mapName + " contains " + currentNumber + " which includes illegal character");
                }
                map.getRangeMask().put(currentNumber.substring(0, currentNumber.length() - 1), true);
            }
            else if(currentNumber.contains("_")){
                indexOfUnderline = currentNumber.indexOf("_");
                if(!isDigit(currentNumber.substring(0, indexOfUnderline)
                + currentNumber.substring(indexOfUnderline + 1))){
                    throw new IllegalCharacterException(mapName + " contains " + currentNumber + " which includes illegal character");
                }
                map.getWildcardMask().put(currentNumber.substring(0, indexOfUnderline), currentNumber.substring(indexOfUnderline + 1));
            }
            else {
                if(!isDigit(currentNumber)){
                    throw new IllegalCharacterException(mapName + " contains " + currentNumber + " which includes illegal character");
                }
                map.getExactMask().put(currentNumber, true);
            }
        }
    }

    private Boolean isDigit(String currentNumber){
        return currentNumber.chars().allMatch(Character::isDigit);
    }

    private boolean mapContains(String currentPhoneNumber, ParsedRequest parsedRequest, String typeOfInputList) {
        if(typeOfInputList.equals("blackList") && parsedRequest == null){
            System.out.println("empty blackList");
            return false;
        }
        else if(typeOfInputList.equals("whiteList") && parsedRequest == null){
            System.out.println("empty whiteList");
            return true;
        }

        currentPhoneNumber = currentPhoneNumber.substring(3); // do not have to consider 994 while checking
        int lenOfCurrentPhoneNumber = currentPhoneNumber.length();
        Map<String, Boolean> map = parsedRequest.getExactMask();

        if(map.containsKey(currentPhoneNumber)){
            System.out.println(typeOfInputList + " exact: true");
            return true;
        }

        map = parsedRequest.getRangeMask();
        for(int i=1; i<lenOfCurrentPhoneNumber; i++){
            if(map.containsKey(currentPhoneNumber.substring(0, i))){
                System.out.println(typeOfInputList + " range: " + true + " " + currentPhoneNumber.substring(0, i));
                return true;
            }
        }

        Map<String, String> map2 = parsedRequest.getWildcardMask();
        String searchedKey, searchedValue;
        for(int i=0; i<lenOfCurrentPhoneNumber; i++){
            searchedKey = currentPhoneNumber.substring(0,i);
            searchedValue = currentPhoneNumber.substring(i+1);
            if(map2.containsKey(searchedKey) && map2.get(searchedKey).equals(searchedValue)){
                System.out.println(typeOfInputList + " wildcard: true " + searchedKey + "_" + searchedValue);
                return true;
            }
        }

        return false;
    }
}
