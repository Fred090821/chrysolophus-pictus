package com.birds.count.chrysolophuspictus.util;

import org.springframework.lang.NonNull;

import java.sql.Timestamp;

/**
 * utility class to return range object containing start and end timestamp
 */
public final class DateRange {

    private final Timestamp from;
    private final Timestamp to;

    private DateRange(Timestamp from, Timestamp to) {
        super();
        this.from = from;
        this.to = to;
    }

    public static DateRange getDateRange(@NonNull Range rangeType) {
        DateRange dr = null;

        switch (rangeType) {
            case TODAY: // from start of today till now
                dr = new DateRange(DateUtility.getTimestampAtStartOfToDay(), DateUtility.fromCurrentTimestamp());
                break;
            // 7 days ago
            case WEEK: //from start of 7 days ago till now
                dr = new DateRange(DateUtility.getTimestampAtStartOfDay(7), DateUtility.fromCurrentTimestamp());
                break;
            // 30 days ago
            case MONTH: //from start of 30 days ago till now
                dr = new DateRange(DateUtility.getTimestampAtStartOfDay(30), DateUtility.fromCurrentTimestamp());
                break;
            default: //range for 15 minutes ago till now
                dr = new DateRange(DateUtility.getTimestampAtStartOfThirtyMinutesAgo(), DateUtility.fromCurrentTimestamp());
        }
        return dr;
    }

    public Timestamp getFrom() {
        return from;
    }

    public Timestamp getTo() {
        return to;
    }
}
