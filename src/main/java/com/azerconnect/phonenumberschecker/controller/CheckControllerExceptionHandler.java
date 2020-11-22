package com.azerconnect.phonenumberschecker.controller;

import com.azerconnect.phonenumberschecker.exception.ExceptionDetails;
import com.azerconnect.phonenumberschecker.exception.NotExistException;
import com.azerconnect.phonenumberschecker.exception.WrongLengthException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalTime;

@RestControllerAdvice
public class CheckControllerExceptionHandler {
    @ExceptionHandler(NotExistException.class)
    public ExceptionDetails notFound(NotExistException exception){
        return new ExceptionDetails(LocalTime.now(),exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(WrongLengthException.class)
    public String wrongLength(){
        return "wrong length Baby";
    }
}
