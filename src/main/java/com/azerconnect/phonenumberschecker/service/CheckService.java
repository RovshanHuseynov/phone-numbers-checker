package com.azerconnect.phonenumberschecker.service;

import com.azerconnect.phonenumberschecker.entity.request.ParsedRequest;
import com.azerconnect.phonenumberschecker.entity.request.Request;
import com.azerconnect.phonenumberschecker.entity.response.Response;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.azerconnect.phonenumberschecker.utils.ValidationUtil.*;
import static com.azerconnect.phonenumberschecker.utils.ParserUtil.*;

@Service
public class CheckService {
    Logger logger = Logger.getLogger(this.getClass());

    public Map<String, String> isEligibleToSell(Request request) {
        logger.info("program started");
        List<String> listMsisdn = request.getMsisdnList();
        String blacklistString = request.getBlacklistString();
        String whitelistString = request.getWhitelistString();

        validateMsisdnList(listMsisdn);

        ParsedRequest blackList = parseRequest(blacklistString, "blacklist");
        ParsedRequest whiteList = parseRequest(whitelistString, "whitelist");

        Response response = new Response();

        for(String currentPhoneNumber : listMsisdn){
            //logger.info(currentPhoneNumber + " is being checked");

            if(listContains(currentPhoneNumber, blackList, "blackList")){
                response.getResponse().put(currentPhoneNumber, "msisdn = " + currentPhoneNumber + " is in blacklist");
            }
            else if(listContains(currentPhoneNumber, whiteList, "whiteList")){
                response.getResponse().put(currentPhoneNumber, "OK");
            }
            else {
                //logger.info("not in whiteList");
                response.getResponse().put(currentPhoneNumber, "msisdn = " + currentPhoneNumber + " is not in whitelist");
            }
        }
        logger.info("program finished");
        logger.info("---------------------------------------");
        return response.getResponse();
    }

    private boolean listContains(String currentPhoneNumber, ParsedRequest parsedRequest, String nameOfList) {
        if(isNull(parsedRequest)){
            if(nameOfList.equals("blackList")){
                //logger.info("empty blackList");
                return false;
            }
            else if(nameOfList.equals("whiteList")){
                //logger.info("empty whiteList");
                return true;
            }
        }

        currentPhoneNumber = currentPhoneNumber.substring(3); // do not have to consider 994 while checking
        Set<String> set = parsedRequest.getExactMask();
        if(set.contains(currentPhoneNumber)){
            //logger.info(currentPhoneNumber + " is found in Exact Mask of " + nameOfList);
            return true;
        }

        int lenOfCurrentPhoneNumber = currentPhoneNumber.length();
        String searchedKey;
        set = parsedRequest.getRangeMask();
        for(int i=1; i<lenOfCurrentPhoneNumber; i++){
            searchedKey = currentPhoneNumber.substring(0, i);
            if(set.contains(searchedKey)){
                //logger.info(searchedKey + " is found in Range Mask of " + nameOfList);
                return true;
            }
        }

        String searchedValue;
        Map<String, String> map = parsedRequest.getWildcardMask();
        for(int i=0; i<lenOfCurrentPhoneNumber; i++){
            searchedKey = currentPhoneNumber.substring(0,i);
            searchedValue = currentPhoneNumber.substring(i+1);
            if(map.containsKey(searchedKey) && map.get(searchedKey).equals(searchedValue)){
                //logger.info(searchedKey + "_" + searchedValue + " is found in Wildcard Mask of " + nameOfList);
                return true;
            }
        }

        return false;
    }
}
