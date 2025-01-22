package com.ggang.be.domain.gongbaekTImeSlot;

import com.ggang.be.domain.constant.WeekDay;
import com.ggang.be.domain.timslot.gongbaekTimeSlot.GongbaekTimeSlotEntity;

public class GongbaekTimeSlotFixture {

    public static GongbaekTimeSlotEntity getTestGongbaekTimeSlot() {
        return GongbaekTimeSlotEntity.builder()
            .startTime(10)
            .endTime(12)
            .weekDay(WeekDay.MON)
            .build();
    }

}
