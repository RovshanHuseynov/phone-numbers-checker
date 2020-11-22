package com.azerconnect.phonenumberschecker.exception;

public class WrongJsonKey extends RuntimeException{
    public WrongJsonKey() {
    }

    public WrongJsonKey(String message) {
        super(message);
    }
}
