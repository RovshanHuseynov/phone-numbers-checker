package com.azerconnect.phonenumberschecker.utils;

import com.azerconnect.phonenumberschecker.entity.request.ParsedRequest;
import com.azerconnect.phonenumberschecker.exception.EmptyRequestException;
import com.azerconnect.phonenumberschecker.exception.IllegalCharacterException;
import com.azerconnect.phonenumberschecker.exception.WrongLengthException;
import org.apache.log4j.Logger;

import java.util.List;

import static com.azerconnect.phonenumberschecker.utils.Constants.*;

public class Utils {
    static Logger logger = Logger.getLogger(Utils.class);

    public static boolean isNull(List<String> list){
        return list == null;
    }

    public static boolean isNull(String string){
        return string == null;
    }

    public static boolean isEmpty(List<String> list){
        return list.size() == 0;
    }

    public static boolean isEmpty(String string){
        return string.length() == 0;
    }

    public static boolean isNull(ParsedRequest parsedRequest){
        return parsedRequest == null;
    }

    public static boolean checkLength(String data, int neededLength){
        return data.length() == neededLength;
    }

    public static boolean isDigit(String data){
        return data.chars().allMatch(Character::isDigit);
    }

    public static void validateMsisdnList(List<String> listMsisdn){
        if(isNull(listMsisdn) || isEmpty(listMsisdn)){
            logger.error("msisdnList is empty");
            throw new EmptyRequestException("msisdnList is empty");
        }

        for(String currentPhoneNumber : listMsisdn){
            if(!isDigit(currentPhoneNumber)){
                logger.error(currentPhoneNumber + " in msisdnList contains illegal character");
                throw new IllegalCharacterException(currentPhoneNumber + " in msisdnList contains illegal character");
            }
            else if(!checkLength(currentPhoneNumber, LENOFONENUMBER)){
                logger.error(currentPhoneNumber + " in msisdnList does not contain " + LENOFONENUMBER + " characters");
                throw new WrongLengthException(currentPhoneNumber + " in msisdnList does not contain " + LENOFONENUMBER + " characters");
            }
        }
    }

    public static void validateOtherList(String searchedKey, String nameOfList){
        if(!isDigit(searchedKey)){
            logger.error(searchedKey + " in " + nameOfList + " contains illegal character");
            throw new IllegalCharacterException(searchedKey + " in " + nameOfList + " contains illegal character");
        }
    }

    public static void validateOtherList(String searchedKey, String searchedValue, String nameOfList){
        if(!isDigit(searchedKey) || !isDigit(searchedValue)){
            logger.error(searchedKey + "_" + searchedValue + " in " + nameOfList + " contains illegal character");
            throw new IllegalCharacterException(searchedKey + "_" + searchedValue + " in " + nameOfList + " contains illegal character");
        }
    }
}
