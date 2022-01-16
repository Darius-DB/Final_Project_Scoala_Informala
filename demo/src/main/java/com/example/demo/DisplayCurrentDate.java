package com.example.demo;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
@Component
public class DisplayCurrentDate {


    private  LocalDate currentDate = LocalDate.of(2021, 12, 15);

    private  Instant instant = currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
    private  Long timeInMillis = instant.toEpochMilli();

    public Long transformDateToLong(LocalDate currentDate) {
        Instant instant = currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        return instant.toEpochMilli();
    }

    public  LocalDate getCurrentDate() {
        return currentDate;
    }

    public  void setCurrentDate(LocalDate currentDate) {
        this.currentDate = currentDate;
    }

    public  Long getTimeInMillis() {
        return timeInMillis;
    }

    public  void setTimeInMillis(Long timeInMillis) {
        this.timeInMillis = timeInMillis;
    }
}
