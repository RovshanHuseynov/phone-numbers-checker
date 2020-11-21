package com.azerconnect.phonenumberschecker.exception;

public class WrongLengthException extends RuntimeException{
    public WrongLengthException() {
    }

    public WrongLengthException(String message) {
        super(message);
    }
}
