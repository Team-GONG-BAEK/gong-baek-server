package com.ggang.be.domain.lectureTimeSlot.fixture;

import com.ggang.be.domain.constant.WeekDay;
import com.ggang.be.domain.timslot.lectureTimeSlot.LectureTimeSlotEntity;
import com.ggang.be.domain.user.fixture.UserEntityFixture;

public class LectureTimeSlotFixture {

    public static LectureTimeSlotEntity makeLectureTimeSlotEntity() {
        return LectureTimeSlotEntity.builder()
            .weekDate(WeekDay.MON)
            .startTime(9.0)
            .endTime(10.0)
            .userEntity(UserEntityFixture.create())
            .build();
    }


}
