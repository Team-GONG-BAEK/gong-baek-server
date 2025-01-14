package com.ggang.be.domain.userOnceGroup.dto;

import com.ggang.be.api.facade.GroupType;
import com.ggang.be.domain.constant.Category;
import com.ggang.be.domain.constant.WeekDate;
import com.ggang.be.domain.group.onceGroup.OnceGroupEntity;

import java.time.LocalDate;

public record NearestOnceGroup(
        long groupId,
        Category category,
        GroupType groupType,
        String groupTitle,
        WeekDate weekDay,
        LocalDate weekDate,
        int currentPeopleCount,
        int maxPeopleCount,
        double startTime,
        double endTime,
        String location
) {
    public static NearestOnceGroup toDto (OnceGroupEntity entity){
        return new NearestOnceGroup(
                entity.getId(),
                entity.getCategory(),
                GroupType.ONCE,
                entity.getTitle(),
                WeekDate.fromDayOfWeek(entity.getGroupDate().getDayOfWeek()),
                entity.getGroupDate(),
                entity.getCurrentPeopleCount(),
                entity.getMaxPeopleCount(),
                entity.getGongbaekTimeSlotEntity().getStartTime(),
                entity.getGongbaekTimeSlotEntity().getEndTime(),
                entity.getLocation()
        );
    }
}