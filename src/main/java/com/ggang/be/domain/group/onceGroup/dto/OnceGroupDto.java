package com.ggang.be.domain.group.onceGroup.dto;

import com.ggang.be.domain.constant.Category;
import com.ggang.be.domain.constant.Status;
import com.ggang.be.domain.constant.WeekDay;
import com.ggang.be.domain.group.onceGroup.OnceGroupEntity;
import com.ggang.be.domain.timslot.gongbaekTimeSlot.GongbaekTimeSlotEntity;
import com.ggang.be.domain.user.UserEntity;

public record OnceGroupDto(
    long groupId,
    Status status,
    String groupTitle,
    String location,
    int currentPeopleCount,
    int maxPeopleCount,
    boolean isHost,
    boolean isApply,
    String introduction,
    Category category,
    int coverImg,
    WeekDay weekDay,
    String weekDate,
    GongbaekTimeSlotEntity gongbaekTimeSlotEntity
) {

    public static OnceGroupDto toDto(OnceGroupEntity entity, UserEntity currentUser) {
        return new OnceGroupDto(
            entity.getId(),
            entity.getStatus(),
            entity.getTitle(),
            entity.getLocation(),
            entity.getCurrentPeopleCount(),
            entity.getMaxPeopleCount(),
            entity.isHost(currentUser),
            entity.isApply(currentUser),
            entity.getIntroduction(),
            entity.getCategory(),
            entity.getCoverImg(),
            WeekDay.fromDayOfWeek(entity.getGroupDate().getDayOfWeek()),
            entity.getGroupDate().toString(),
            GongbaekTimeSlotEntity.builder()
                .startTime(entity.getGongbaekTimeSlotEntity().getStartTime())
                .endTime(entity.getGongbaekTimeSlotEntity().getEndTime())
                .weekDay(WeekDay.fromDayOfWeek(entity.getGroupDate().getDayOfWeek()))
                .userEntity(currentUser)
                .build()
        );
    }

}
