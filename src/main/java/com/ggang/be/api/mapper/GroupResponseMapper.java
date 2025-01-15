package com.ggang.be.api.mapper;

import com.ggang.be.api.group.dto.GroupResponse;
import com.ggang.be.api.group.dto.NearestGroupResponse;
import com.ggang.be.domain.constant.GroupType;
import com.ggang.be.domain.group.everyGroup.dto.EveryGroupDetail;
import com.ggang.be.domain.group.onceGroup.dto.OnceGroupDto;
import com.ggang.be.domain.userEveryGroup.dto.NearestEveryGroup;
import com.ggang.be.domain.userOnceGroup.dto.NearestOnceGroup;

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

    public static GroupResponse fromEveryGroup(EveryGroupDetail dto) {
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
            dto.weekDay(),
            null,
            dto.startTime(),
            dto.endTime()
        );
    }

    public static NearestGroupResponse toNearestGroupResponse(NearestEveryGroup nearestEveryGroup) {
        return new NearestGroupResponse(
                nearestEveryGroup.groupId(),
                nearestEveryGroup.category(),
                GroupType.WEEKLY,
                nearestEveryGroup.groupTitle(),
                nearestEveryGroup.weekDay(),
                nearestEveryGroup.weekDate().toString(),
                nearestEveryGroup.currentPeopleCount(),
                nearestEveryGroup.maxPeopleCount(),
                nearestEveryGroup.startTime(),
                nearestEveryGroup.endTime(),
                nearestEveryGroup.location()
        );
    }

    public static NearestGroupResponse toNearestGroupResponse(NearestOnceGroup nearestOnceGroup) {
        return new NearestGroupResponse(
                nearestOnceGroup.groupId(),
                nearestOnceGroup.category(),
                GroupType.ONCE,
                nearestOnceGroup.groupTitle(),
                nearestOnceGroup.weekDay(),
                nearestOnceGroup.weekDate().toString(),
                nearestOnceGroup.currentPeopleCount(),
                nearestOnceGroup.maxPeopleCount(),
                nearestOnceGroup.startTime(),
                nearestOnceGroup.endTime(),
                nearestOnceGroup.location()
        );
    }
}
