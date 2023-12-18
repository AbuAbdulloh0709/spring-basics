package com.epam.esm.handler;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

@Component
public class DateHandler {

    public String getCurrentDate() {
        LocalDateTime localDateTime = now();
        return localDateTime.toString();
    }
}
