package com.ggang.be.domain.lectureTimeSlot.vo;

import com.ggang.be.domain.constant.WeekDate;
import com.ggang.be.domain.lectureTimeSlot.LectureTimeSlotEntity;
import com.ggang.be.domain.user.UserEntity;

public record LectureTimeSlotVo(WeekDate weekDate, Double startTime, Double endTime) {

    public static LectureTimeSlotEntity toEntity(LectureTimeSlotVo lectureTimeSlotVo, UserEntity userEntity) {
        return LectureTimeSlotEntity.builder().
            weekDate(lectureTimeSlotVo.weekDate()).
            startTime(lectureTimeSlotVo.startTime()).
            endTime(lectureTimeSlotVo.endTime()).
            userEntity(userEntity).
            build();
    }
}
