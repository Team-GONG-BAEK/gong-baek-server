package com.ggang.be.domain.timslot.vo;

import com.ggang.be.domain.constant.WeekDate;
import com.ggang.be.domain.timslot.lectureTimeSlot.LectureTimeSlotEntity;

public record ReadCommonInvalidTimeVo(
    WeekDate weekDate,
    double startTime,
    double endTime
) {
    public static ReadCommonInvalidTimeVo fromLectureEntity(
        LectureTimeSlotEntity lectureTimeSlotEntity) {
        return new ReadCommonInvalidTimeVo(lectureTimeSlotEntity.getWeekDate(),
            lectureTimeSlotEntity.getStartTime(), lectureTimeSlotEntity.getEndTime());
    }
}
