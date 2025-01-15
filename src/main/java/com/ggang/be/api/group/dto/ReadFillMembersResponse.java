package com.ggang.be.api.group.dto;

import com.ggang.be.domain.group.everyGroup.EveryGroupEntity;
import com.ggang.be.domain.group.onceGroup.OnceGroupEntity;
import com.ggang.be.domain.userEveryGroup.dto.FillMember;

import java.util.List;

public record ReadFillMembersResponse(int maxPeopleCount, int currentPeopleCount, List<FillMember> members) {
    public static ReadFillMembersResponse ofEveryGroup(EveryGroupEntity findEveryGroupEntity,
        List<FillMember> everyGroupUsersInfos) {
        return new ReadFillMembersResponse(findEveryGroupEntity.getMaxPeopleCount(),
            findEveryGroupEntity.getCurrentPeopleCount(),  everyGroupUsersInfos);
    }

    public static ReadFillMembersResponse ofOnceGroup(OnceGroupEntity findOnceGroupEntity,
        List<FillMember> everyGroupUsersInfos) {
        return new ReadFillMembersResponse(findOnceGroupEntity.getMaxPeopleCount(),
            findOnceGroupEntity.getCurrentPeopleCount(),  everyGroupUsersInfos);
    }
}
