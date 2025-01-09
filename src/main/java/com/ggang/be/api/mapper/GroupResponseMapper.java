package com.ggang.be.api.mapper;

import com.ggang.be.api.common.GroupResponse;
import com.ggang.be.domain.constant.GroupType;
import com.ggang.be.domain.constant.WeekDate;
import com.ggang.be.domain.everyGroup.EveryGroupEntity;
import com.ggang.be.domain.onceGroup.OnceGroupEntity;
import com.ggang.be.domain.user.UserEntity;

public class GroupResponseMapper {

    public static GroupResponse fromOnceGroup(OnceGroupEntity entity, UserEntity currentUser) {
        return new GroupResponse(
                entity.getId(),
                GroupType.ONCE.toString(),
                entity.getTitle(),
                entity.getLocation(),
                entity.getStatus().isActive(),
                entity.isHost(currentUser),
                entity.isApply(currentUser),
                entity.getCurrentPeopleCount(),
                entity.getMaxPeopleCount(),
                entity.getIntroduction(),
                entity.getCategory().toString(),
                entity.getCoverImg(),
                WeekDate.fromDayOfWeek(entity.getGroupDate().getDayOfWeek()).toString(),
                entity.getGroupDate().toString(),
                entity.getStartTime(),
                entity.getEndTime()
        );
    }

    public static GroupResponse fromEveryGroup(EveryGroupEntity entity, UserEntity currentUser) {
        return new GroupResponse(
                entity.getId(),
                GroupType.WEEKLY.toString(),
                entity.getTitle(),
                entity.getLocation(),
                entity.getStatus().isActive(),
                entity.isHost(currentUser),
                entity.isApply(currentUser),
                entity.getCurrentPeopleCount(),
                entity.getMaxPeopleCount(),
                entity.getIntroduction(),
                entity.getCategory().toString(),
                entity.getCoverImg(),
                entity.getWeekDate().toString(),
                null,
                entity.getStartTime(),
                entity.getEndTime()
        );
    }
}
