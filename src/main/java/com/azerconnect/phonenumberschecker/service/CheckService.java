package com.azerconnect.phonenumberschecker.service;

import com.azerconnect.phonenumberschecker.entity.request.ParsedRequest;
import com.azerconnect.phonenumberschecker.entity.request.Request;
import com.azerconnect.phonenumberschecker.entity.response.Response;
import com.azerconnect.phonenumberschecker.exception.EmptyRequestException;
import com.azerconnect.phonenumberschecker.exception.IllegalCharacterException;
import com.azerconnect.phonenumberschecker.exception.WrongLengthException;
import com.azerconnect.phonenumberschecker.utils.Constants;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.azerconnect.phonenumberschecker.utils.Constants.*;
import static com.azerconnect.phonenumberschecker.utils.Utils.*;

@Service
public class CheckService {
    public Response IsEligibleToSell(Request request) {
        List<String> listMsisdn = request.getMsisdnList();
        String blacklistString = request.getBlacklistString();
        String whitelistString = request.getWhitelistString();

        if(isNull(listMsisdn) || isEmpty(listMsisdn)){
            throw new EmptyRequestException("msisdnList is empty");
        }

        for(String currentPhoneNumber : listMsisdn){
            if(!isDigit(currentPhoneNumber)){
                throw new IllegalCharacterException(currentPhoneNumber + " in msisdnList contains illegal character");
            }
            else if(!checkLength(currentPhoneNumber, LENOFONENUMBER)){
                throw new WrongLengthException(currentPhoneNumber + " in msisdnList does not contain " + LENOFONENUMBER + " characters");
            }
        }

        ParsedRequest blackList = parseRequest(blacklistString, "blacklist");
        ParsedRequest whiteList = parseRequest(whitelistString, "whitelist");

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

    private ParsedRequest parseRequest(String listData, String nameOfList){
        if(isNull(listData) || isEmpty(listData)){
            System.out.println(nameOfList + " ");
            return null;
        }

        ParsedRequest parsedRequest = new ParsedRequest();
        String[] splitData = listData.split(",");

        int indexOfUnderline;
        String searchedKey, searchedValue;
        for(String currentNumber : splitData){
            if(currentNumber.endsWith("%")){
                searchedKey = currentNumber.substring(0, currentNumber.length() - 1);

                if(!isDigit(searchedKey)){
                    throw new IllegalCharacterException(searchedKey + " in " + nameOfList + " contains illegal character");
                }

                parsedRequest.getRangeMask().add(searchedKey);
            }
            else if(currentNumber.contains("_")){
                indexOfUnderline = currentNumber.indexOf("_");
                searchedKey = currentNumber.substring(0, indexOfUnderline);
                searchedValue = currentNumber.substring(indexOfUnderline + 1);

                if(!isDigit(searchedKey) || !isDigit(searchedValue)){
                    throw new IllegalCharacterException(currentNumber + " in " + nameOfList + " contains illegal character");
                }

                parsedRequest.getWildcardMask().put(searchedKey, searchedValue);
            }
            else {
                if(!isDigit(currentNumber)){
                    throw new IllegalCharacterException(currentNumber + " in " + nameOfList + " contains illegal character");
                }

                parsedRequest.getExactMask().add(currentNumber);
            }
        }

        return parsedRequest;
    }

    private boolean listContains(String currentPhoneNumber, ParsedRequest parsedRequest, String nameOfList) {
        if(isNull(parsedRequest)){
            if(nameOfList.equals("blackList")){
                System.out.println("empty blackList");
                return false;
            }
            else if(nameOfList.equals("whiteList")){
                System.out.println("empty whiteList");
                return true;
            }
        }

        currentPhoneNumber = currentPhoneNumber.substring(3); // do not have to consider 994 while checking
        Set<String> set = parsedRequest.getExactMask();
        if(set.contains(currentPhoneNumber)){
            System.out.println(nameOfList + " exact: true");
            return true;
        }

        int lenOfCurrentPhoneNumber = currentPhoneNumber.length();
        String searchedKey;
        set = parsedRequest.getRangeMask();
        for(int i=1; i<lenOfCurrentPhoneNumber; i++){
            searchedKey = currentPhoneNumber.substring(0, i);
            if(set.contains(searchedKey)){
                System.out.println(nameOfList + " range: " + true + " " + currentPhoneNumber.substring(0, i));
                return true;
            }
        }

        String searchedValue;
        Map<String, String> map = parsedRequest.getWildcardMask();
        for(int i=0; i<lenOfCurrentPhoneNumber; i++){
            searchedKey = currentPhoneNumber.substring(0,i);
            searchedValue = currentPhoneNumber.substring(i+1);
            if(map.containsKey(searchedKey) && map.get(searchedKey).equals(searchedValue)){
                System.out.println(nameOfList + " wildcard: " + true + " " + searchedKey + "_" + searchedValue);
                return true;
            }
        }

        return false;
    }
}
