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

        tempList = request.getWhitelistString().split(",");
        for(String currentWhiteListString : tempList){
            if(currentWhiteListString.contains("%")) whiteList.getRangeMask().add(currentWhiteListString.substring(0, currentWhiteListString.length() - 1));
            else if(currentWhiteListString.contains("_")) whiteList.getWildcardMask().add(currentWhiteListString);
            else whiteList.getExactMask().add(currentWhiteListString);
        }

        boolean IsBlackList = false;
        boolean IsWhiteList = true;
        int indexOfUnderline;

        for(String currentPhoneNumber : request.getMsisdnList()){
            IsBlackList = false;

            for(String currentBlackListExactMask : blackList.getExactMask()){
                if(currentPhoneNumber.equals(currentBlackListExactMask)) IsBlackList = true;
                if(IsBlackList) break;
            }

            if(IsBlackList){
                response.getResponse().put(currentPhoneNumber, "msisdn = " + currentPhoneNumber + " is in blacklist");
                continue;
            }

            for(String currentBlackListRangeMask : blackList.getRangeMask()){
                if(currentPhoneNumber.substring(0, currentBlackListRangeMask.length()).equals(currentBlackListRangeMask)) IsBlackList = true;
                if(IsBlackList) break;
            }

            if(IsBlackList){
                response.getResponse().put(currentPhoneNumber, "msisdn = " + currentPhoneNumber + " is in blacklist");
                continue;
            }

            for(String currentBlackListWildcardMask : blackList.getWildcardMask()){
                indexOfUnderline = currentBlackListWildcardMask.indexOf("_");
                if(currentPhoneNumber.substring(3, indexOfUnderline + 3).equals(currentBlackListWildcardMask.substring(0, indexOfUnderline))
                && currentPhoneNumber.substring(4 + indexOfUnderline).equals(currentBlackListWildcardMask.substring(indexOfUnderline + 1))){
                    IsBlackList = true;
                }
                if(IsBlackList) break;
            }

            response.getResponse().put(currentPhoneNumber, "ok");

            //response.getResponse().put(s, "msisdn = " + s + " is not in whilelist");
        }

        return response;
    }
}
