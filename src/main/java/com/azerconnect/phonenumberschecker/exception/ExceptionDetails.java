package com.azerconnect.phonenumberschecker.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalTime;

@Data
@AllArgsConstructor
public class ExceptionDetails {
    private final LocalTime time;
    private final String message;
}
