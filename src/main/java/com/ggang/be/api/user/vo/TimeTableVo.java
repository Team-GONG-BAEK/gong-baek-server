package com.ggang.be.api.user.vo;

import com.ggang.be.domain.constant.WeekDay;
import com.ggang.be.domain.timslot.lectureTimeSlot.vo.LectureTimeSlotVo;

public record TimeTableVo(WeekDay weekDay,
                          Double startTime,
                          Double endTime) {

    public static LectureTimeSlotVo toLectureTimeSlotVo(final TimeTableVo timeTableVo) {
        return new LectureTimeSlotVo(
                timeTableVo.weekDay(),
                timeTableVo.startTime(),
                timeTableVo.endTime()
        );
    }
}
