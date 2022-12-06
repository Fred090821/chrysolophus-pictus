package com.birds.count.chrysolophuspictus.util;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

/**
 * Utility class to manage the range
 * Uses the system default time zone
 */
public final class DateUtility {

    private DateUtility() {
    }

    /**
     * Convert current LocalDateTime to Timestamp
     *
     * @return Timestamp
     */
    public static Timestamp fromCurrentTimestamp() {
        return Timestamp.from(LocalDateTime.now().toInstant(ZoneOffset.UTC));
    }

    /**
     * Uses the current local date to the start of the day from the day in the past chosen
     *
     * @param fromDay - the past day to start from
     * @return Timestamp - timestamp at exactly fromDay ago
     */
    public static Timestamp getTimestampAtStartOfDay(int fromDay) {
        return Timestamp.from(LocalDate.now().minusDays(fromDay).atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Uses the current local date to the start of today
     *
     * @return Timestamp - timestamp at exactly start of today
     */
    public static Timestamp getTimestampAtStartOfToDay() {
        return Timestamp.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    /**
     * get zoned timestamp 15 minutes ago
     *
     * @return timestamp at 15 minutes ago in current time zone
     */
    public static Timestamp getTimestampAtStartOfThirtyMinutesAgo() {
        return Timestamp.from(LocalDateTime.now().toInstant(ZoneOffset.UTC).minus(15, ChronoUnit.MINUTES));
    }

}
