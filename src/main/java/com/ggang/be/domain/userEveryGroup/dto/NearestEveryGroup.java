package com.ggang.be.domain.userEveryGroup.dto;

import com.ggang.be.domain.constant.Category;
import com.ggang.be.domain.constant.GroupType;
import com.ggang.be.domain.constant.WeekDate;
import com.ggang.be.domain.group.everyGroup.EveryGroupEntity;

import java.time.LocalDate;

public record NearestEveryGroup(
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
    public static NearestEveryGroup toDto (EveryGroupEntity entity){
        return new NearestEveryGroup(
                entity.getId(),
                entity.getCategory(),
                GroupType.WEEKLY,
                entity.getTitle(),
                entity.getGongbaekTimeSlotEntity().getWeekDate(),
                WeekDate.getNextMeetingDate(entity.getGongbaekTimeSlotEntity().getWeekDate()),
                entity.getCurrentPeopleCount(),
                entity.getMaxPeopleCount(),
                entity.getGongbaekTimeSlotEntity().getStartTime(),
                entity.getGongbaekTimeSlotEntity().getEndTime(),
                entity.getLocation()
        );
    }
}