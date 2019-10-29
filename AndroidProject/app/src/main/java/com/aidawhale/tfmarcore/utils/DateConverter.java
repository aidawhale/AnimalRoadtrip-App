package com.aidawhale.tfmarcore.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

//    @TypeConverter
//    public static Date fromTimestamp(Long value) {
//        return value == null ? null : new Date(value);
//
//    }
//
//    @TypeConverter
//    public static Long dateToTimestamp(Date date) {
//        return date == null ? null : date.getTime();
//    }

    public static String complexDateToSimpleDate(Date date) {
        return sdf.format(date);
    }
}
