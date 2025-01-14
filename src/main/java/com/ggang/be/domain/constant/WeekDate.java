package com.ggang.be.domain.constant;

import java.time.DayOfWeek;
import java.time.LocalDate;

public enum WeekDate {
    MON("Monday"),
    TUE("Tuesday"),
    WED("Wednesday"),
    THU("Thursday"),
    FRI("Friday"),
    SAT("Saturday"),
    SUN("Sunday");

    private final String fullName;

    WeekDate(String fullName) {
        this.fullName = fullName;
    }

    public static WeekDate fromDayOfWeek(DayOfWeek dayOfWeek) {
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

    public static DayOfWeek getDayOfWeekFromWeekDate(WeekDate weekDate) {
        return switch (weekDate) {
            case MON -> DayOfWeek.MONDAY;
            case TUE -> DayOfWeek.TUESDAY;
            case WED -> DayOfWeek.WEDNESDAY;
            case THU -> DayOfWeek.THURSDAY;
            case FRI -> DayOfWeek.FRIDAY;
            case SAT -> DayOfWeek.SATURDAY;
            case SUN -> DayOfWeek.SUNDAY;
        };
    }

    public static LocalDate getNextMeetingDate(WeekDate weekDate) {
        LocalDate today = LocalDate.now();
        DayOfWeek targetDay = getDayOfWeekFromWeekDate(weekDate);
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
