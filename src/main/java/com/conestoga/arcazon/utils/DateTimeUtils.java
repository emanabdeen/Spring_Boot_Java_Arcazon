package com.conestoga.arcazon.utils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {


    public static String formatDateTime(Instant dateTime) {
        if (dateTime != null) {
            return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                    .withZone(ZoneId.systemDefault())
                    .format(dateTime);
        } else {
            return "";
        }
    }
}
