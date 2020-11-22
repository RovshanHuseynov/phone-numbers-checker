package com.azerconnect.phonenumberschecker.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalTime;

@Data
@AllArgsConstructor
public class ExceptionDetails {
    private final LocalTime localTime;
    private final String message;
    private final HttpStatus httpStatus;
}
