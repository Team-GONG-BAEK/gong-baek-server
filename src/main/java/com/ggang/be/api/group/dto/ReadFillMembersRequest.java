package com.ggang.be.api.group.dto;

import com.ggang.be.api.facade.GroupType;
import com.ggang.be.domain.group.dto.ReadEveryGroupMember;
import com.ggang.be.domain.group.dto.ReadOnceGroupMember;
import com.ggang.be.domain.group.everyGroup.EveryGroupEntity;
import com.ggang.be.domain.group.onceGroup.OnceGroupEntity;

public record ReadFillMembersRequest(Long groupId, GroupType groupType) {

    public static ReadEveryGroupMember toEveryGroupMemberInfo(EveryGroupEntity everyGroupEntity) {
        return new ReadEveryGroupMember(everyGroupEntity);
    }


    public static ReadOnceGroupMember toOnceGroupMemberInfo(OnceGroupEntity onceGroupEntity) {
        return new ReadOnceGroupMember(onceGroupEntity);
    }
}
