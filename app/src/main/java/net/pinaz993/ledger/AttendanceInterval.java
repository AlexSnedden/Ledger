package net.pinaz993.ledger;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;

import org.joda.time.Duration;
import org.joda.time.Instant;
import org.joda.time.Interval;
import org.joda.time.LocalDate;


public class AttendanceInterval{

    Duration duration;
    Instant start;
    Interval interval;

    public static long getCurrentStart(){
        return getCurrent().start.getMillis();
    }

    public static AttendanceInterval getCurrent() {
        Instant now = new Instant();
        return getFromInstant(now);
    }

    public static AttendanceInterval getFromInstant(Instant then){
        LocalDate now = then.toDateTime().toLocalDate();
        Instant newStart;
        newStart = now.toDateTimeAtStartOfDay().toInstant();

        return new AttendanceInterval(newStart);
    }

    // To ensure proper alignment, all initializations will take place in static methods.
    private AttendanceInterval(Instant start) {
        this.start = start;
        duration = new Duration(86400000); //Number of milliseconds in a day
        interval = new Interval(start, duration);
    }

    public boolean isInInterval(Instant test) {
        return interval.contains(test);
    }

    public Instant getStart() {
        return start;
    }
}