package com.thermal.thermalback.util;

import lombok.experimental.UtilityClass;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

@UtilityClass
public class UtilTimeService {

    public static final String SERVICE_TIMEZONE = "UTC";

    public static LocalDateTime getLocalDateNow() {
        return LocalDateTime.ofInstant(Instant.now(), ZoneId.of(SERVICE_TIMEZONE));
    }

    public static Instant toInstant(LocalDateTime nowUtc) {
        return nowUtc.toInstant(ZoneOffset.UTC);
    }
}
