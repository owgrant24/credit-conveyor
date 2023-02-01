package com.example.conveyor.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.*;
import java.util.TimeZone;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TimeUtil {

    private static final TimeZone REAL_TIME_ZONE = TimeZone.getDefault();
    private static Clock clock = Clock.systemDefaultZone();


    public static LocalDate currentDate() {
        return LocalDate.now(getClock());
    }

    public static LocalTime currentTime() {
        return LocalTime.now(getClock());
    }

    public static LocalDateTime currentDateTime() {
        return LocalDateTime.now(getClock());
    }

    public static OffsetDateTime currentOffsetDateTime() {
        return OffsetDateTime.now(getClock());
    }

    public static ZonedDateTime currentZonedDateTime() {
        return ZonedDateTime.now(getClock());
    }

    public static Instant currentInstant() {
        return Instant.now(getClock());
    }

    public static long currentTimeMillis() {
        return currentInstant().toEpochMilli();
    }

    public static void useMockTime(LocalDateTime dateTime, ZoneId zoneId) {
        Instant instant = dateTime.atZone(zoneId).toInstant();
        clock = Clock.fixed(instant, zoneId);
        TimeZone.setDefault(TimeZone.getTimeZone(zoneId));
    }

    public static void useSystemDefaultZoneClock() {
        TimeZone.setDefault(REAL_TIME_ZONE);
        clock = Clock.systemDefaultZone();
    }

    private static Clock getClock() {
        return clock;
    }
}
