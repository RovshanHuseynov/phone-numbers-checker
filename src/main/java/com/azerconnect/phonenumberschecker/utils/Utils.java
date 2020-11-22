package com.azerconnect.phonenumberschecker.utils;

import com.azerconnect.phonenumberschecker.entity.request.Request;
import com.azerconnect.phonenumberschecker.exception.EmptyRequestException;
import com.azerconnect.phonenumberschecker.exception.IllegalCharacterException;
import com.azerconnect.phonenumberschecker.exception.NotExistException;
import com.azerconnect.phonenumberschecker.exception.WrongLengthException;

import java.util.List;

public class Utils {
    public static boolean isNull(List<String> listMsisdn){
        return listMsisdn == null;
    }

    public static void checkLength(String data, int neededLength, String nameOfObject){
        if(data.length() != neededLength){
            throw new WrongLengthException(data + " in " + nameOfObject + " does not contain " + neededLength + " character");
        }
    }

    public static void validateRequestt(Request request) {
        if(request.getMsisdnList() == null){
            throw new NotExistException("msisdnList does not exist in Request");
        }
        else if(request.getMsisdnList().size() == 0){
            throw new EmptyRequestException("msisdnList is empty");
        }
        else if(request.getBlacklistString() == null){
            throw new NotExistException("blacklistString does not exist in Request");
        }
        else if(request.getWhitelistString() == null){
            throw new NotExistException("whitelistString does not exist in Request");
        }
    }

    public static void isDigit(String data, String nameOfObject){
        if(!data.chars().allMatch(Character::isDigit)){
            throw new IllegalCharacterException(data + " in " + nameOfObject + " does not contains illegal character");
        }
    }
}
