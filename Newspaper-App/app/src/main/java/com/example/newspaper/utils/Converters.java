package com.example.newspaper.utils;

import androidx.room.TypeConverter;

import com.example.newspaper.models.TypeEmotion;

import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Converters {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

    @TypeConverter
    public static LocalDate stringToLocalDate(String value) {
        return value == null ? null : LocalDate.parse(value, formatter);
    }

    @TypeConverter
    public static String localDateToString(LocalDate date) {
        return date == null ? null : date.format(formatter);
    }

    @TypeConverter
    public static Instant stringtoInstant(String value) {
        return value == null ? null : Instant.parse(value);
    }

    @TypeConverter
    public static String instantToString(Instant date) {
        return date == null ? null : date.toString();
    }

    @TypeConverter
    public static TypeEmotion stringToTypeEmotion(String value) {
        return value == null ? null : TypeEmotion.valueOf(value);
    }

    @TypeConverter
    public static String typeEmotionToString(TypeEmotion status) {
        return status == null ? null : status.name();
    }

    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
