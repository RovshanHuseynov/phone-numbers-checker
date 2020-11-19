package com.azerconnect.phonenumberschecker.service;

import com.azerconnect.phonenumberschecker.entity.BlackList;
import com.azerconnect.phonenumberschecker.entity.WhiteList;
import com.azerconnect.phonenumberschecker.entity.request.Request;
import com.azerconnect.phonenumberschecker.entity.response.Response;

@org.springframework.stereotype.Service
public class Service {
    public Response IsEligibleToSell(Request request){
        Response response = new Response();
        BlackList blackList = new BlackList();
        WhiteList whiteList = new WhiteList();

        String[] tempList = request.getBlacklistString().split(",");
        for(String currentBlackListString : tempList){
            if(currentBlackListString.contains("%")) blackList.getRangeMask().add(currentBlackListString.substring(0, currentBlackListString.length() - 1));
            else if(currentBlackListString.contains("_")) blackList.getWildcardMask().add(currentBlackListString);
            else blackList.getExactMask().add(currentBlackListString);
        }

        /*System.out.println(blackList.getRangeMask().size());
        System.out.println(blackList.getWildcardMask().size());
        System.out.println(blackList.getExactMask().size());*/

        tempList = request.getWhitelistString().split(",");
        for(String currentWhiteListString : tempList){
            if(currentWhiteListString.contains("%")) whiteList.getRangeMask().add(currentWhiteListString.substring(0, currentWhiteListString.length() - 1));
            else if(currentWhiteListString.contains("_")) whiteList.getWildcardMask().add(currentWhiteListString);
            else whiteList.getExactMask().add(currentWhiteListString);
        }

        /*System.out.println(whiteList.getRangeMask().size());
        System.out.println(whiteList.getWildcardMask().size());
        System.out.println(whiteList.getExactMask().size());*/

        boolean isBlackList;
        boolean isWhiteList;
        int indexOfUnderline;

        for(String currentPhoneNumber : request.getMsisdnList()){
            System.out.println(currentPhoneNumber + " is being checked");
            currentPhoneNumber = currentPhoneNumber.substring(3); // do not have to consider 994 while checking
            isBlackList = false;
            isWhiteList = false;

            for(String currentBlackListExactMask : blackList.getExactMask()){
                if(currentPhoneNumber.equals(currentBlackListExactMask)) isBlackList = true;
                if(isBlackList) break;
            }

            System.out.println("blackList exact: " + isBlackList);

            if(isBlackList){
                response.getResponse().put("994" + currentPhoneNumber, "msisdn = 994" + currentPhoneNumber + " is in blacklist");
                continue;
            }

            for(String currentBlackListRangeMask : blackList.getRangeMask()){
                if(currentPhoneNumber.startsWith(currentBlackListRangeMask)) isBlackList = true;
                if(isBlackList) break;
            }

            System.out.println("blackList range: " + isBlackList);

            if(isBlackList){
                response.getResponse().put("994" + currentPhoneNumber, "msisdn = 994" + currentPhoneNumber + " is in blacklist");
                continue;
            }

            for(String currentBlackListWildcardMask : blackList.getWildcardMask()){
                indexOfUnderline = currentBlackListWildcardMask.indexOf("_");
                if(currentPhoneNumber.startsWith(currentBlackListWildcardMask.substring(0, indexOfUnderline))
                && currentPhoneNumber.endsWith(currentBlackListWildcardMask.substring(indexOfUnderline + 1))){
                    isBlackList = true;
                }
                if(isBlackList) break;
            }

            System.out.println("blackList wildCard: " + isBlackList);

            if(isBlackList){
                response.getResponse().put("994" + currentPhoneNumber, "msisdn = 994" + currentPhoneNumber + " is in blacklist");
                continue;
            }

            for(String currentWhiteListExactMask : whiteList.getExactMask()){
                if(currentPhoneNumber.equals(currentWhiteListExactMask)) isWhiteList = true;
                if(isWhiteList) break;
            }

            System.out.println("whiteList exact: " + isWhiteList);

            if(isWhiteList){
                response.getResponse().put("994" + currentPhoneNumber, "ok");
                continue;
            }

            for(String currentWhiteListRangeMask : whiteList.getRangeMask()){
                if(currentPhoneNumber.startsWith(currentWhiteListRangeMask)) isWhiteList = true;
                if(isWhiteList) break;
            }

            System.out.println("whiteList range: " + isWhiteList);

            if(isWhiteList){
                response.getResponse().put("994" + currentPhoneNumber, "ok");
                continue;
            }

            for(String currentWhiteListWildcardMask : whiteList.getWildcardMask()){
                indexOfUnderline = currentWhiteListWildcardMask.indexOf("_");
                if(currentPhoneNumber.startsWith(currentWhiteListWildcardMask.substring(0, indexOfUnderline))
                        && currentPhoneNumber.endsWith(currentWhiteListWildcardMask.substring(indexOfUnderline + 1))){
                    isWhiteList = true;
                }
                if(isWhiteList) break;
            }

            System.out.println("whiteList wildCard: " + isWhiteList);

            if(isWhiteList) {
                response.getResponse().put("994" + currentPhoneNumber, "ok");
                continue;
            }

            response.getResponse().put("994" + currentPhoneNumber, "msisdn = 994" + currentPhoneNumber + " is not in whilelist");
        }

        return response;
    }
}
