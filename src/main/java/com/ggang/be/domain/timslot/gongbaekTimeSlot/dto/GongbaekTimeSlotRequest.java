package com.ggang.be.domain.timslot.gongbaekTimeSlot.dto;

import com.ggang.be.domain.constant.WeekDay;
import com.ggang.be.domain.user.UserEntity;

public record GongbaekTimeSlotRequest(UserEntity userEntity, double startTime, double endTime, WeekDay weekDay) {
}
