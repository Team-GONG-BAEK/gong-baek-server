package com.ggang.be.api.group.onceGroup.strategy;

import com.ggang.be.api.group.dto.GroupRequest;
import com.ggang.be.api.group.onceGroup.service.OnceGroupService;
import com.ggang.be.api.group.registry.DeleteGroupStrategy;
import com.ggang.be.domain.constant.GroupType;
import com.ggang.be.domain.group.onceGroup.OnceGroupEntity;
import com.ggang.be.domain.user.UserEntity;
import com.ggang.be.global.annotation.Strategy;
import lombok.RequiredArgsConstructor;

@Strategy
@RequiredArgsConstructor
public class DeleteOnceGroupStrategy implements DeleteGroupStrategy {
    private final OnceGroupService onceGroupService;

    @Override
    public boolean support(GroupType groupType) {
        return groupType.equals(GroupType.ONCE);
    }

    @Override
    public void deleteGroup(UserEntity userEntity, GroupRequest request) {
        OnceGroupEntity onceGroupEntity = onceGroupService.findOnceGroupEntityByGroupId(request.groupId());
        onceGroupService.deleteOnceGroup(userEntity, onceGroupEntity);
    }
}
