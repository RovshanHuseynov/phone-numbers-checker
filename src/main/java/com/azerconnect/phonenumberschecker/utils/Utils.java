package com.azerconnect.phonenumberschecker.utils;

import com.azerconnect.phonenumberschecker.entity.request.ParsedRequest;

import java.util.List;

public class Utils {
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
}
