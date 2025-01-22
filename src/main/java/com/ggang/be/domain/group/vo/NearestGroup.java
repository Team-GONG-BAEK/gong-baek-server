package com.ggang.be.domain.group.vo;

import com.ggang.be.domain.constant.Category;
import com.ggang.be.domain.constant.GroupType;
import com.ggang.be.domain.constant.WeekDay;
import com.ggang.be.domain.group.everyGroup.EveryGroupEntity;
import com.ggang.be.domain.group.onceGroup.OnceGroupEntity;
import java.time.LocalDate;

public record NearestGroup(long groupId,
                           Category category,
                           GroupType groupType,
                           String groupTitle,
                           WeekDay weekDay,
                           LocalDate weekDate,
                           int currentPeopleCount,
                           int maxPeopleCount,
                           double startTime,
                           double endTime,
                           String location) {

    public static NearestGroup fromEveryEntity(EveryGroupEntity entity){
        return new NearestGroup(
            entity.getId(),
            entity.getCategory(),
            GroupType.WEEKLY,
            entity.getTitle(),
            entity.getGongbaekTimeSlotEntity().getWeekDay(),
            WeekDay.getNextMeetingDate(entity.getGongbaekTimeSlotEntity().getWeekDay()),
            entity.getCurrentPeopleCount(),
            entity.getMaxPeopleCount(),
            entity.getGongbaekTimeSlotEntity().getStartTime(),
            entity.getGongbaekTimeSlotEntity().getEndTime(),
            entity.getLocation()
        );
    }

    public static NearestGroup fromOnceEntity(OnceGroupEntity entity){
        return new NearestGroup(
            entity.getId(),
            entity.getCategory(),
            GroupType.ONCE,
            entity.getTitle(),
            WeekDay.fromDayOfWeek(entity.getGroupDate().getDayOfWeek()),
            entity.getGroupDate(),
            entity.getCurrentPeopleCount(),
            entity.getMaxPeopleCount(),
            entity.getGongbaekTimeSlotEntity().getStartTime(),
            entity.getGongbaekTimeSlotEntity().getEndTime(),
            entity.getLocation()
        );
    }


}
