package com.azerconnect.phonenumberschecker.service;

import com.azerconnect.phonenumberschecker.entity.request.InputList;
import com.azerconnect.phonenumberschecker.entity.request.Request;
import com.azerconnect.phonenumberschecker.entity.response.Response;
import com.azerconnect.phonenumberschecker.exception.IllegalCharacterException;

import java.util.Map;

@org.springframework.stereotype.Service
public class Service {
    private InputList blackList;
    private InputList whiteList;
    private int lenOfOneNumber;

    public Response IsEligibleToSell(Request request){
        Response response = new Response();
        blackList = new InputList();
        whiteList = new InputList();
        lenOfOneNumber = request.getMsisdnList().get(0).length() - 3;    // do not have to consider 994 while checking

        parseRequest("blacklistString", request.getBlacklistString());

        System.out.println("black range " + blackList.getRangeMask().size());
        for(String s : blackList.getRangeMask().keySet()){
            System.out.print(s + " ");
        }
        System.out.println();
        System.out.println("black wild " + blackList.getWildcardMask().size());
        for(String s : blackList.getWildcardMask().keySet()){
            System.out.print(s + "_" + blackList.getWildcardMask().get(s) + " ");
        }
        System.out.println();
        System.out.println("black exact " + blackList.getExactMask().size());
        for(String s : blackList.getExactMask().keySet()){
            System.out.print(s + " ");
        }
        System.out.println();

        parseRequest("whitelistString", request.getWhitelistString());

        System.out.println("white range " + whiteList.getRangeMask().size());
        for(String s : whiteList.getRangeMask().keySet()){
            System.out.print(s + " ");
        }
        System.out.println();
        System.out.println("white wild " + whiteList.getWildcardMask().size());
        for(String s : whiteList.getWildcardMask().keySet()){
            System.out.print(s + "_" + whiteList.getWildcardMask().get(s) + " ");
        }
        System.out.println();
        System.out.println("white exact " + whiteList.getExactMask().size());
        for(String s : whiteList.getExactMask().keySet()){
            System.out.print(s + " ");
        }
        System.out.println();

        for(String currentPhoneNumber : request.getMsisdnList()){
            if(!isDigit(currentPhoneNumber)){
                throw new IllegalCharacterException("msisdnList contains " + currentPhoneNumber + " which includes illegal character");
            }

            System.out.println(currentPhoneNumber + " is being checked");
            currentPhoneNumber = currentPhoneNumber.substring(3); // do not have to consider 994 while checking

            if(request.getBlacklistString().length() == 0 && request.getWhitelistString().length() == 0){
                response.getResponse().put("994" + currentPhoneNumber, "ok");
            }
            else if(mapContains(currentPhoneNumber, blackList, "blackList")){
                response.getResponse().put("994" + currentPhoneNumber, "msisdn = 994" + currentPhoneNumber + " is in blacklist");
            }
            else if(mapContains(currentPhoneNumber, whiteList, "whiteList")){
                response.getResponse().put("994" + currentPhoneNumber, "ok");
            }
            else {
                System.out.println("not in whiteList");
                response.getResponse().put("994" + currentPhoneNumber, "msisdn = 994" + currentPhoneNumber + " is not in whitelist");
            }
        }

        return response;
    }

    private void parseRequest(String listName, String inputData){
        if(inputData.length() == 0){
            return;
        }

        String[] splitData = inputData.split(",");

        switch (listName){
            case "blacklistString" :
                fillMap(listName, blackList, splitData);
                break;
            case "whitelistString" :
                fillMap(listName, whiteList, splitData);
                break;
            default:
                System.out.println("Exception");
        }
    }

    private void fillMap(String mapName, InputList map, String[] splitData) {
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

    private boolean mapContains(String currentPhoneNumber, InputList inputList, String nameOfInputList) {
        Map<String, Boolean> map = inputList.getExactMask();
        if(map.containsKey(currentPhoneNumber)){
            System.out.println(nameOfInputList + " exact: true");
            return true;
        }

        map = inputList.getRangeMask();
        for(int i=1; i<lenOfOneNumber; i++){
            if(map.containsKey(currentPhoneNumber.substring(0, i))){
                System.out.println(nameOfInputList + " range: " + true + " " + currentPhoneNumber.substring(0, i));
                return true;
            }
        }

        Map<String, String> map2 = inputList.getWildcardMask();
        String searchedKey, searchedValue;
        for(int i=0; i<lenOfOneNumber; i++){
            searchedKey = currentPhoneNumber.substring(0,i);
            searchedValue = currentPhoneNumber.substring(i+1);
            if(map2.containsKey(searchedKey) && map2.get(searchedKey).equals(searchedValue)){
                System.out.println(nameOfInputList + " wildcard: true " + searchedKey + "_" + searchedValue);
                return true;
            }
        }

        return false;
    }
}
