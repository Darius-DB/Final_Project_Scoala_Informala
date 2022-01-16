package com.example.demo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    static SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    public static long getDeliveryDateInMillis(String date) {
        Date deliveryDate;
        try {
            deliveryDate = sdf.parse(date.trim());
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
        return deliveryDate.getTime();
    }
}
