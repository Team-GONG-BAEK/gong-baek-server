package com.ggang.be.api.group.everyGroup.strategy;

import com.ggang.be.api.group.dto.GroupRequest;
import com.ggang.be.api.group.everyGroup.service.EveryGroupService;
import com.ggang.be.api.group.registry.DeleteGroupStrategy;
import com.ggang.be.domain.constant.GroupType;
import com.ggang.be.domain.group.everyGroup.EveryGroupEntity;
import com.ggang.be.domain.user.UserEntity;
import com.ggang.be.global.annotation.Strategy;
import lombok.RequiredArgsConstructor;

@Strategy
@RequiredArgsConstructor
public class DeleteEveryGroupStrategy implements DeleteGroupStrategy {
    private final EveryGroupService everyGroupService;

    @Override
    public boolean support(GroupType groupType) {
        return groupType.equals(GroupType.WEEKLY);
    }

    @Override
    public void deleteGroup(UserEntity userEntity, GroupRequest request) {
        EveryGroupEntity everyGroupEntity = everyGroupService.findEveryGroupEntityByGroupId(request.groupId());
        everyGroupService.deleteEveryGroup(userEntity, everyGroupEntity);
    }
}
