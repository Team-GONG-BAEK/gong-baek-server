package com.ggang.be.domain.timslot.lectureTimeSlot.dto;

import com.ggang.be.domain.constant.WeekDay;
import com.ggang.be.domain.user.UserEntity;

public record LectureTimeSlotRequest(UserEntity userEntity, double startTime, double endTime, WeekDay weekDay) {

    public static LectureTimeSlotRequest of(UserEntity userEntity, double startTime, double endTime, WeekDay weekDay) {
        return new LectureTimeSlotRequest(userEntity, startTime, endTime, weekDay);
    }
}
