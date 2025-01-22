package com.ggang.be.domain.timslot.vo;

import com.ggang.be.domain.constant.WeekDay;
import com.ggang.be.domain.timslot.lectureTimeSlot.LectureTimeSlotEntity;

public record ReadCommonInvalidTimeVo(
    int idx,
    WeekDay weekDay,
    double startTime,
    double endTime
) {
    public static ReadCommonInvalidTimeVo fromLectureEntity(
        int idx,
        LectureTimeSlotEntity lectureTimeSlotEntity) {
        return new ReadCommonInvalidTimeVo(idx, lectureTimeSlotEntity.getWeekDay(),
            lectureTimeSlotEntity.getStartTime(), lectureTimeSlotEntity.getEndTime());
    }
}
