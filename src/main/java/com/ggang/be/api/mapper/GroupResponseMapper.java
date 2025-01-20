package com.ggang.be.api.mapper;

import com.ggang.be.api.group.dto.GroupResponse;
import com.ggang.be.api.group.dto.NearestGroupResponse;
import com.ggang.be.domain.constant.GroupType;
import com.ggang.be.domain.group.everyGroup.dto.EveryGroupDto;
import com.ggang.be.domain.group.onceGroup.dto.OnceGroupDto;

public record GroupResponseMapper() {
    public static GroupResponse fromOnceGroup(OnceGroupDto dto) {
        return new GroupResponse(
            dto.groupId(),
            GroupType.ONCE.toString(),
            dto.groupTitle(),
            dto.location(),
            dto.status(),
            dto.isHost(),
            dto.isApply(),
            dto.currentPeopleCount(),
            dto.maxPeopleCount(),
            dto.introduction(),
            dto.category(),
            dto.coverImg(),
            dto.weekDay(),
            dto.weekDate() != null ? dto.weekDate() : null,
            dto.gongbaekTimeSlotEntity().getStartTime(),
            dto.gongbaekTimeSlotEntity().getEndTime()
        );
    }

    public static GroupResponse fromEveryGroup(EveryGroupDto dto) {
        return new GroupResponse(
            dto.groupId(),
            GroupType.WEEKLY.toString(),
            dto.title(),
            dto.location(),
            dto.status(),
            dto.isHost(),
            dto.isApply(),
            dto.currentPeopleCount(),
            dto.maxPeopleCount(),
            dto.introduction(),
            dto.category(),
            dto.coverImg(),
            dto.gongbaekTimeSlotEntity().getWeekDate(),
            null,
            dto.gongbaekTimeSlotEntity().getStartTime(),
            dto.gongbaekTimeSlotEntity().getEndTime()
        );
    }


    public static NearestGroupResponse toNearestGroupResponse(
        com.ggang.be.api.group.facade.NearestGroup nearestGroup) {
        return new NearestGroupResponse(
            nearestGroup.groupId(),
            nearestGroup.category(),
            nearestGroup.groupType(),
            nearestGroup.groupTitle(),
            nearestGroup.weekDay(),
            nearestGroup.weekDate().toString(),
            nearestGroup.currentPeopleCount(),
            nearestGroup.maxPeopleCount(),
            nearestGroup.startTime(),
            nearestGroup.endTime(),
            nearestGroup.location()
        );
    }
}
