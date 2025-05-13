package com.example.newspaper.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    private static final SimpleDateFormat DISPLAY_FORMAT = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    private static final SimpleDateFormat DB_FORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    public static String formatDate(Date date) {
        if (date == null) return "";
        return DISPLAY_FORMAT.format(date);
    }

    public static String formatDate(int day, int month, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        return DISPLAY_FORMAT.format(calendar.getTime());
    }

    public static Date parseDate(String dateString) {
        try {
            return DISPLAY_FORMAT.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String toDatabaseFormat(Date date) {
        if (date == null) return null;
        return DB_FORMAT.format(date);
    }
}