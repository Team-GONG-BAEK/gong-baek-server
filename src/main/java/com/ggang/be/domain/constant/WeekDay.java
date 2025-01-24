package com.ggang.be.domain.constant;

import java.time.DayOfWeek;
import java.time.LocalDate;

public enum WeekDay {
    MON,
    TUE,
    WED,
    THU,
    FRI,
    SAT,
    SUN;

    public static WeekDay fromDayOfWeek(DayOfWeek dayOfWeek) {
        return switch (dayOfWeek) {
            case MONDAY -> MON;
            case TUESDAY -> TUE;
            case WEDNESDAY -> WED;
            case THURSDAY -> THU;
            case FRIDAY -> FRI;
            case SATURDAY -> SAT;
            case SUNDAY -> SUN;
        };
    }

    public static DayOfWeek getDayOfWeekFromWeekDate(WeekDay weekDay) {
        return switch (weekDay) {
            case MON -> DayOfWeek.MONDAY;
            case TUE -> DayOfWeek.TUESDAY;
            case WED -> DayOfWeek.WEDNESDAY;
            case THU -> DayOfWeek.THURSDAY;
            case FRI -> DayOfWeek.FRIDAY;
            case SAT -> DayOfWeek.SATURDAY;
            case SUN -> DayOfWeek.SUNDAY;
        };
    }

    public static LocalDate getNextMeetingDate(WeekDay weekDay) {
        LocalDate today = LocalDate.now();
        DayOfWeek targetDay = getDayOfWeekFromWeekDate(weekDay);
        LocalDate nextMeetingDate = today;

        while (nextMeetingDate.getDayOfWeek() != targetDay) {
            nextMeetingDate = nextMeetingDate.plusDays(1);
        }

        if (nextMeetingDate.isAfter(today)) {
            return nextMeetingDate;
        }

        return nextMeetingDate.plusWeeks(1);
    }
}
