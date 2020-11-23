package com.azerconnect.phonenumberschecker.exception;

public class WrongJSONKeyException extends RuntimeException{
    public WrongJSONKeyException() {
    }

    public WrongJSONKeyException(String message) {
        super(message);
    }
}
