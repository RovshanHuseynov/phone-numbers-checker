package com.azerconnect.phonenumberschecker.utils;

import com.azerconnect.phonenumberschecker.entity.request.ParsedRequest;

import static com.azerconnect.phonenumberschecker.utils.ValidationUtil.*;
import static com.azerconnect.phonenumberschecker.utils.ValidationUtil.validateOtherList;

public class ParserUtil {
    public static ParsedRequest parseRequest(String data, String nameOfData){
        if(isNull(data) || isEmpty(data)){
            return null;
        }

        ParsedRequest parsedRequest = new ParsedRequest();
        String[] splitData = data.split(",");

        int indexOfUnderline;
        String searchedKey, searchedValue;
        for(String currentNumber : splitData){
            if(currentNumber.endsWith("%")){
                searchedKey = currentNumber.substring(0, currentNumber.length() - 1);
                validateOtherList(searchedKey, nameOfData);
                parsedRequest.getRangeMask().add(searchedKey);
            }
            else if(currentNumber.contains("_")){
                indexOfUnderline = currentNumber.indexOf("_");
                searchedKey = currentNumber.substring(0, indexOfUnderline);
                searchedValue = currentNumber.substring(indexOfUnderline + 1);
                validateOtherList(searchedKey, searchedValue, nameOfData);
                parsedRequest.getWildcardMask().put(searchedKey, searchedValue);
            }
            else {
                validateOtherList(currentNumber, nameOfData);
                parsedRequest.getExactMask().add(currentNumber);
            }
        }

        return parsedRequest;
    }
}
