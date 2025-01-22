package com.ggang.be.domain.timslot.lectureTimeSlot.vo;

import com.ggang.be.domain.constant.WeekDay;
import com.ggang.be.domain.timslot.lectureTimeSlot.LectureTimeSlotEntity;
import com.ggang.be.domain.user.UserEntity;

public record LectureTimeSlotVo(WeekDay weekDay, Double startTime, Double endTime) {

    public static LectureTimeSlotEntity toEntity(LectureTimeSlotVo lectureTimeSlotVo, UserEntity userEntity) {
        return LectureTimeSlotEntity.builder().
            weekDay(lectureTimeSlotVo.weekDay()).
            startTime(lectureTimeSlotVo.startTime()).
            endTime(lectureTimeSlotVo.endTime()).
            userEntity(userEntity).
            build();
    }
}
