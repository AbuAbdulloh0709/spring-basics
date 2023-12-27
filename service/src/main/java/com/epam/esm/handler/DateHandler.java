package com.epam.esm.handler;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

@Component
public class DateHandler {

    public LocalDateTime getCurrentDate() {
        return now();
    }
}
