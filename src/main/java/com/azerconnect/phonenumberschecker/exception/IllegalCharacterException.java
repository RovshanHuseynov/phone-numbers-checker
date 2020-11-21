package com.azerconnect.phonenumberschecker.exception;

public class IllegalCharacterException extends RuntimeException{
    public IllegalCharacterException() {
    }

    public IllegalCharacterException(String message) {
        super(message);
    }
}
