package com.ggang.be.api.mapper;

import com.ggang.be.api.facade.GroupType;
import com.ggang.be.api.group.dto.GroupResponse;
import com.ggang.be.domain.group.everyGroup.dto.EveryGroupDetail;
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
                dto.startTime(),
                dto.endTime()
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
}
