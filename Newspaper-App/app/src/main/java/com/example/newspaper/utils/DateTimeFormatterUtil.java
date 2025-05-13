package com.example.newspaper.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

public class DateTimeFormatterUtil {

    private Map<Long, Function<Instant, String>> strategyMap = new LinkedHashMap<>();

    public DateTimeFormatterUtil(){
        strategyMap.put(60L, this::formatInSeconds);
        strategyMap.put(3600L, this::formatInMinutes);
        strategyMap.put(86400L, this::formatInHours);
        strategyMap.put(Long.MAX_VALUE, this::formatInDays);
    }

    public String formatInSeconds(Instant instant){
        var seconds = ChronoUnit.SECONDS.between(instant, Instant.now());
        return seconds + " giây trước";
    }

    public String formatInMinutes(Instant instant){
        var minutes = ChronoUnit.MINUTES.between(instant, Instant.now());
        return minutes + " phút trước";
    }

    public String formatInHours(Instant instant){
        var hours = ChronoUnit.HOURS.between(instant, Instant.now());
        return hours + " giờ trước";
    }

    public String formatInDays(Instant instant){
        LocalDateTime ldt = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
        return ldt.format(formatter);
    }

    public String format(Instant instant) {
        long seconds = ChronoUnit.SECONDS.between(instant, Instant.now());
        return strategyMap.entrySet().stream()
                .filter(longFunctionEntry -> seconds < longFunctionEntry.getKey())
                .findFirst().get().getValue().apply(instant);
    }
}
