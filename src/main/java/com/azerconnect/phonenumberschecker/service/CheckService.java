package com.azerconnect.phonenumberschecker.service;

import com.azerconnect.phonenumberschecker.entity.request.ParsedRequest;
import com.azerconnect.phonenumberschecker.entity.request.Request;
import com.azerconnect.phonenumberschecker.entity.response.Response;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.azerconnect.phonenumberschecker.utils.Utils.*;

@Service
public class CheckService {
    Logger logger = Logger.getLogger(this.getClass());

    public Response IsEligibleToSell(Request request) {

        logger.info("program started");
        List<String> listMsisdn = request.getMsisdnList();
        String blacklistString = request.getBlacklistString();
        String whitelistString = request.getWhitelistString();

        validateMsisdnList(listMsisdn, logger);

        ParsedRequest blackList = parseRequest(blacklistString, "blacklist");
        ParsedRequest whiteList = parseRequest(whitelistString, "whitelist");

        Response response = new Response();
        for(String currentPhoneNumber : listMsisdn){
            logger.info(currentPhoneNumber + " is being checked");

            if(listContains(currentPhoneNumber, blackList, "blackList")){
                response.getResponse().put(currentPhoneNumber, "msisdn = " + currentPhoneNumber + " is in blacklist");
            }
            else if(listContains(currentPhoneNumber, whiteList, "whiteList")){
                response.getResponse().put(currentPhoneNumber, "ok");
            }
            else {
                logger.info("not in whiteList");
                response.getResponse().put(currentPhoneNumber, "msisdn = " + currentPhoneNumber + " is not in whitelist");
            }
        }
        logger.info("program finished");
        logger.info("---------------------------------------");
        return response;
    }

    private ParsedRequest parseRequest(String listData, String nameOfList){
        if(isNull(listData) || isEmpty(listData)){
            return null;
        }

        ParsedRequest parsedRequest = new ParsedRequest();
        String[] splitData = listData.split(",");

        int indexOfUnderline;
        String searchedKey, searchedValue;
        for(String currentNumber : splitData){
            if(currentNumber.endsWith("%")){
                searchedKey = currentNumber.substring(0, currentNumber.length() - 1);
                validateOtherList(searchedKey, nameOfList, logger);
                parsedRequest.getRangeMask().add(searchedKey);
            }
            else if(currentNumber.contains("_")){
                indexOfUnderline = currentNumber.indexOf("_");
                searchedKey = currentNumber.substring(0, indexOfUnderline);
                searchedValue = currentNumber.substring(indexOfUnderline + 1);
                validateOtherList(searchedKey, searchedValue, nameOfList, logger);
                parsedRequest.getWildcardMask().put(searchedKey, searchedValue);
            }
            else {
                validateOtherList(currentNumber, nameOfList, logger);
                parsedRequest.getExactMask().add(currentNumber);
            }
        }

        return parsedRequest;
    }

    private boolean listContains(String currentPhoneNumber, ParsedRequest parsedRequest, String nameOfList) {
        if(isNull(parsedRequest)){
            if(nameOfList.equals("blackList")){
                logger.info("empty blackList");
                return false;
            }
            else if(nameOfList.equals("whiteList")){
                logger.info("empty whiteList");
                return true;
            }
        }

        currentPhoneNumber = currentPhoneNumber.substring(3); // do not have to consider 994 while checking
        Set<String> set = parsedRequest.getExactMask();
        if(set.contains(currentPhoneNumber)){
            logger.info(nameOfList + " exact: true");
            return true;
        }

        int lenOfCurrentPhoneNumber = currentPhoneNumber.length();
        String searchedKey;
        set = parsedRequest.getRangeMask();
        for(int i=1; i<lenOfCurrentPhoneNumber; i++){
            searchedKey = currentPhoneNumber.substring(0, i);
            if(set.contains(searchedKey)){
                logger.info(nameOfList + " range: " + true + " " + currentPhoneNumber.substring(0, i));
                return true;
            }
        }

        String searchedValue;
        Map<String, String> map = parsedRequest.getWildcardMask();
        for(int i=0; i<lenOfCurrentPhoneNumber; i++){
            searchedKey = currentPhoneNumber.substring(0,i);
            searchedValue = currentPhoneNumber.substring(i+1);
            if(map.containsKey(searchedKey) && map.get(searchedKey).equals(searchedValue)){
                logger.info(nameOfList + " wildcard: " + true + " " + searchedKey + "_" + searchedValue);
                return true;
            }
        }

        return false;
    }
}
