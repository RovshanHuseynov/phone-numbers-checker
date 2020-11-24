package com.azerconnect.phonenumberschecker.controller;

import com.azerconnect.phonenumberschecker.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalTime;

@RestControllerAdvice
public class CheckControllerExceptionHandler {
    @ExceptionHandler(EmptyRequestException.class)
    public ExceptionDetails handleEmptyRequestException(EmptyRequestException exception){
        return new ExceptionDetails(LocalTime.now(),exception.getMessage());
    }

    @ExceptionHandler(IllegalCharacterException.class)
    public ExceptionDetails handleIllegalCharacterException(IllegalCharacterException exception){
        return new ExceptionDetails(LocalTime.now(),exception.getMessage());
    }

    @ExceptionHandler(WrongLengthException.class)
    public ExceptionDetails handleWrongLengthException(WrongLengthException exception){
        return new ExceptionDetails(LocalTime.now(),exception.getMessage());
    }
}
