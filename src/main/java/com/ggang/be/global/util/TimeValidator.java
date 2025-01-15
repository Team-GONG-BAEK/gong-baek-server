package com.ggang.be.global.util;

import com.ggang.be.api.common.ResponseError;
import com.ggang.be.api.exception.GongBaekException;
import com.ggang.be.domain.constant.WeekDate;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@Slf4j
public class TimeValidator {
    public static  void isWeekDateRight(WeekDate writeWeekDate, LocalDate writeDate) {
        WeekDate realWeekDate = WeekDate.fromDayOfWeek(writeDate.getDayOfWeek());
        if(!realWeekDate.equals(writeWeekDate)) {
            log.error("weekDate and weekDay is not same");
            throw new GongBaekException(ResponseError.BAD_REQUEST);
        }
    }

    public static void isTimeValid(double startTime, double endTime){
        if(startTime < 0 || endTime > 24) {
            log.error("startTime or endTime is not valid");
            throw new GongBaekException(ResponseError.BAD_REQUEST);
        }
        if(startTime > endTime) {
            log.error("startTime or endTime is not valid");
            throw new GongBaekException(ResponseError.BAD_REQUEST);
        }
    }

    public static void isDateBeforeNow(LocalDate writeDate){
        if(writeDate.isBefore(LocalDate.now())) {
            log.error("writeDate {} is before now {}", writeDate, LocalDate.now());
            throw new GongBaekException(ResponseError.BAD_REQUEST);
        }
    }
}
