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
        int indexOfUnderline;

        parseAccordingToInputType("blackList", request.getBlacklistString());

        /*System.out.println(blackList.getRangeMask().size());
        for(String s : blackList.getRangeMask().keySet()){
            System.out.print(s + " ");
        }
        System.out.println();
        System.out.println(blackList.getWildcardMask().size());
        for(String s : blackList.getWildcardMask().keySet()){
            System.out.print(s + " ");
        }
        System.out.println();
        System.out.println(blackList.getExactMask().size());
        for(String s : blackList.getExactMask().keySet()){
            System.out.print(s + " ");
        }
        System.out.println();*/

        parseAccordingToInputType("whiteList", request.getWhitelistString());

        /*System.out.println(whiteList.getRangeMask().size());
        for(String s : whiteList.getRangeMask().keySet()){
            System.out.print(s + " ");
        }
        System.out.println();
        System.out.println(whiteList.getWildcardMask().size());
        for(String s : whiteList.getWildcardMask().keySet()){
            System.out.print(s + " ");
        }
        System.out.println();
        System.out.println(whiteList.getExactMask().size());
        for(String s : whiteList.getExactMask().keySet()){
            System.out.print(s + " ");
        }
        System.out.println();*/

        for(String currentPhoneNumber : request.getMsisdnList()){
            System.out.println(currentPhoneNumber + " is being checked");
            currentPhoneNumber = currentPhoneNumber.substring(3); // do not have to consider 994 while checking
            isBlackList = false;
            isWhiteList = request.getWhitelistString().length() == 0;

            if(request.getBlacklistString().length() == 0 && isWhiteList){
                System.out.println("here -------------------");
                response.getResponse().put("994" + currentPhoneNumber, "ok");
                continue;
            }

            if(blackList.getExactMask().containsKey(currentPhoneNumber)){
                System.out.println("blackList exact: true");
                response.getResponse().put("994" + currentPhoneNumber, "msisdn = 994" + currentPhoneNumber + " is in blacklist");
                continue;
            }

            Map<String, Boolean> temp = blackList.getRangeMask();
            for(int i=1; i<=8; i++){
                if(temp.containsKey(currentPhoneNumber.substring(0, i))){
                    System.out.println("blackList range: true " + currentPhoneNumber.substring(0, i));
                    response.getResponse().put("994" + currentPhoneNumber, "msisdn = 994" + currentPhoneNumber + " is in blacklist");
                    isBlackList = true;
                    break;
                }
            }

            if(isBlackList){
                response.getResponse().put("994" + currentPhoneNumber, "msisdn = 994" + currentPhoneNumber + " is in blacklist");
                continue;
            }

            for(String currentBlackListWildcardMask : blackList.getWildcardMask().keySet()){
                indexOfUnderline = currentBlackListWildcardMask.indexOf("_");
                if(currentPhoneNumber.startsWith(currentBlackListWildcardMask.substring(0, indexOfUnderline))
                && currentPhoneNumber.endsWith(currentBlackListWildcardMask.substring(indexOfUnderline + 1))){
                    isBlackList = true;
                    break;
                }
            }

            if(isBlackList){
                System.out.println("blackList wildcard: true");
                response.getResponse().put("994" + currentPhoneNumber, "msisdn = 994" + currentPhoneNumber + " is in blacklist");
                continue;
            }

            if(whiteList.getExactMask().containsKey(currentPhoneNumber)){
                System.out.println("whiteList exact: true");
                response.getResponse().put("994" + currentPhoneNumber, "ok");
                continue;
            }

            temp = whiteList.getRangeMask();
            for(int i=1; i<=8; i++){
                if(temp.containsKey(currentPhoneNumber.substring(0, i))){
                    System.out.println("whiteList range: true " + currentPhoneNumber.substring(0, i));
                    response.getResponse().put("994" + currentPhoneNumber, "msisdn = 994" + currentPhoneNumber + " is in whiteList");
                    isWhiteList = true;
                    break;
                }
            }

            if(isWhiteList){
                response.getResponse().put("994" + currentPhoneNumber, "ok");
                continue;
            }


            if(whiteList.getRangeMask().containsKey(currentPhoneNumber)){
                System.out.println("whiteList range: true");
                response.getResponse().put("994" + currentPhoneNumber, "ok");
                continue;
            }

            for(String currentWhiteListWildcardMask : whiteList.getWildcardMask().keySet()){
                indexOfUnderline = currentWhiteListWildcardMask.indexOf("_");
                if(currentPhoneNumber.startsWith(currentWhiteListWildcardMask.substring(0, indexOfUnderline))
                        && currentPhoneNumber.endsWith(currentWhiteListWildcardMask.substring(indexOfUnderline + 1))){
                    isWhiteList = true;
                    break;
                }
            }

            if(isWhiteList) {
                System.out.println("whiteList wildCard: " + isWhiteList);
                response.getResponse().put("994" + currentPhoneNumber, "ok");
            }
            else{
                response.getResponse().put("994" + currentPhoneNumber, "msisdn = 994" + currentPhoneNumber + " is not in whitelist");
            }
        }

        return response;
    }

    public void parseAccordingToInputType(String listType, String list){
        String[] splitList = list.split(",");

        switch (listType){
            case "blackList" :
                for(String currentBlackListNumber : splitList){
                    if(currentBlackListNumber.contains("%")) blackList.getRangeMask().put(currentBlackListNumber.substring(0, currentBlackListNumber.length() - 1), true);
                    else if(currentBlackListNumber.contains("_")) blackList.getWildcardMask().put(currentBlackListNumber, true);
                    else blackList.getExactMask().put(currentBlackListNumber, true);
                }
                break;
            case "whiteList" :
                for(String currentWhiteListNumber : splitList){
                    if(currentWhiteListNumber.contains("%")) whiteList.getRangeMask().put(currentWhiteListNumber.substring(0, currentWhiteListNumber.length() - 1), true);
                    else if(currentWhiteListNumber.contains("_")) whiteList.getWildcardMask().put(currentWhiteListNumber, true);
                    else whiteList.getExactMask().put(currentWhiteListNumber, true);
                }
                break;
            default:
                System.out.println("Exception");
        }
    }
}
