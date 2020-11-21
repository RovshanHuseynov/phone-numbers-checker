package com.azerconnect.phonenumberschecker.service;

import com.azerconnect.phonenumberschecker.entity.request.InputList;
import com.azerconnect.phonenumberschecker.entity.request.Request;
import com.azerconnect.phonenumberschecker.entity.response.Response;

import java.util.Map;

@org.springframework.stereotype.Service
public class Service {
    private InputList blackList;
    private InputList whiteList;

    public Response IsEligibleToSell(Request request){
        Response response = new Response();
        blackList = new InputList();
        whiteList = new InputList();
        boolean isBlackList;
        boolean isWhiteList;
        int lenOfOneNumber = request.getMsisdnList().get(0).length() - 3;    // do not have to consider 994 while checking

        fillMapsAccordingToRequest("blackList", request.getBlacklistString());

        System.out.println(blackList.getRangeMask().size());
        for(String s : blackList.getRangeMask().keySet()){
            System.out.print(s + " ");
        }
        System.out.println();
        System.out.println(blackList.getWildcardMask().size());
        for(String s : blackList.getWildcardMask().keySet()){
            System.out.print(s + "_" + blackList.getWildcardMask().get(s) + " ");
        }
        System.out.println();
        System.out.println(blackList.getExactMask().size());
        for(String s : blackList.getExactMask().keySet()){
            System.out.print(s + " ");
        }
        System.out.println();

        fillMapsAccordingToRequest("whiteList", request.getWhitelistString());

        System.out.println(whiteList.getRangeMask().size());
        for(String s : whiteList.getRangeMask().keySet()){
            System.out.print(s + " ");
        }
        System.out.println();
        System.out.println(whiteList.getWildcardMask().size());
        for(String s : whiteList.getWildcardMask().keySet()){
            System.out.print(s + "_" + whiteList.getWildcardMask().get(s) + " ");
        }
        System.out.println();
        System.out.println(whiteList.getExactMask().size());
        for(String s : whiteList.getExactMask().keySet()){
            System.out.print(s + " ");
        }
        System.out.println();

        for(String currentPhoneNumber : request.getMsisdnList()){
            System.out.println(currentPhoneNumber + " is being checked");
            currentPhoneNumber = currentPhoneNumber.substring(3); // do not have to consider 994 while checking
            isBlackList = request.getBlacklistString().length() == 0;
            isWhiteList = request.getWhitelistString().length() == 0;

            if(!isBlackList && isWhiteList){
                response.getResponse().put("994" + currentPhoneNumber, "ok");
                continue;
            }

            if(blackList.getExactMask().containsKey(currentPhoneNumber)){
                System.out.println("blackList exact: true");
                response.getResponse().put("994" + currentPhoneNumber, "msisdn = 994" + currentPhoneNumber + " is in blacklist");
                continue;
            }

            Map<String, Boolean> temp = blackList.getRangeMask();
            for(int i=1; i<lenOfOneNumber; i++){
                if(temp.containsKey(currentPhoneNumber.substring(0, i))){
                    System.out.println("blackList range: " + isBlackList + " " + currentPhoneNumber.substring(0, i));
                    response.getResponse().put("994" + currentPhoneNumber, "msisdn = 994" + currentPhoneNumber + " is in blacklist");
                    isBlackList = true;
                    break;
                }
            }

            if(isBlackList){
                //response.getResponse().put("994" + currentPhoneNumber, "msisdn = 994" + currentPhoneNumber + " is in blacklist");
                continue;
            }

            Map<String, String> temp1 = blackList.getWildcardMask();
            String searchedKey, searchedValue;
            for(int i=0; i<lenOfOneNumber; i++){
                searchedKey = currentPhoneNumber.substring(0,i);
                searchedValue = currentPhoneNumber.substring(i+1);
                if(temp1.containsKey(searchedKey) && temp1.get(searchedKey).equals(searchedValue)){
                    System.out.println("blackList wildcard: true " + searchedKey + "_" + searchedValue);
                    response.getResponse().put("994" + currentPhoneNumber, "msisdn = 994" + currentPhoneNumber + " is in blacklist");
                    isBlackList = true;
                    break;
                }
            }

            if(isBlackList){
                //response.getResponse().put("994" + currentPhoneNumber, "msisdn = 994" + currentPhoneNumber + " is in blacklist");
                continue;
            }

            if(whiteList.getExactMask().containsKey(currentPhoneNumber)){
                System.out.println("whiteList exact: " + true);
                response.getResponse().put("994" + currentPhoneNumber, "ok");
                continue;
            }

            temp = whiteList.getRangeMask();
            for(int i=1; i<lenOfOneNumber; i++){
                if(temp.containsKey(currentPhoneNumber.substring(0, i))){
                    System.out.println("whiteList range: true " + currentPhoneNumber.substring(0, i));
                    response.getResponse().put("994" + currentPhoneNumber, "ok");
                    isWhiteList = true;
                    break;
                }
            }

            if(isWhiteList){
                //response.getResponse().put("994" + currentPhoneNumber, "ok");
                continue;
            }

            temp1 = whiteList.getWildcardMask();
            for(int i=0; i<lenOfOneNumber; i++){
                searchedKey = currentPhoneNumber.substring(0,i);
                searchedValue = currentPhoneNumber.substring(i+1);
                if(temp1.containsKey(searchedKey) && temp1.get(searchedKey).equals(searchedValue)){
                    System.out.println("whiteList wildcard: true " + searchedKey + "_" + searchedValue);
                    response.getResponse().put("994" + currentPhoneNumber, "ok");
                    isWhiteList = true;
                    break;
                }
            }

            if(!isWhiteList) {
                System.out.println("not in whiteList");
                response.getResponse().put("994" + currentPhoneNumber, "msisdn = 994" + currentPhoneNumber + " is not in whitelist");
            }

            System.out.println("-----------------------------------------");
        }

        return response;
    }

    public void fillMapsAccordingToRequest(String mapName, String inputData){
        String[] splitData = inputData.split(",");

        switch (mapName){
            case "blackList" :
                fillMap(blackList, splitData);
                break;
            case "whiteList" :
                fillMap(whiteList, splitData);
                break;
            default:
                System.out.println("Exception");
        }
    }

    private void fillMap(InputList map, String[] splitData) {
        int indexOfUnderline;
        for(String currentNumber : splitData){
            if(currentNumber.contains("%")){
                map.getRangeMask().put(currentNumber.substring(0, currentNumber.length() - 1), true);
            }
            else if(currentNumber.contains("_")){
                indexOfUnderline = currentNumber.indexOf("_");
                map.getWildcardMask().put(currentNumber.substring(0, indexOfUnderline), currentNumber.substring(indexOfUnderline + 1));
            }
            else map.getExactMask().put(currentNumber, true);
        }
    }
}
