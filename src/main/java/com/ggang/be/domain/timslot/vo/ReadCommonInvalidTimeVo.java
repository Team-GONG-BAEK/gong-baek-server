package com.ggang.be.domain.timslot.vo;

import com.ggang.be.domain.constant.WeekDate;
import com.ggang.be.domain.timslot.lectureTimeSlot.LectureTimeSlotEntity;

public record ReadCommonInvalidTimeVo(
    int idx,
    WeekDate weekDate,
    double startTime,
    double endTime
) {
    public static ReadCommonInvalidTimeVo fromLectureEntity(
        int idx,
        LectureTimeSlotEntity lectureTimeSlotEntity) {
        return new ReadCommonInvalidTimeVo(idx, lectureTimeSlotEntity.getWeekDate(),
            lectureTimeSlotEntity.getStartTime(), lectureTimeSlotEntity.getEndTime());
    }
}
