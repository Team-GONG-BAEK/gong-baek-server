package com.ggang.be.api.group.onceGroup.strategy;

import com.ggang.be.api.common.ResponseError;
import com.ggang.be.api.exception.GongBaekException;
import com.ggang.be.api.group.dto.GroupRequest;
import com.ggang.be.api.group.onceGroup.service.OnceGroupService;
import com.ggang.be.api.group.registry.CancelGroupStrategy;
import com.ggang.be.api.userOnceGroup.service.UserOnceGroupService;
import com.ggang.be.domain.constant.GroupType;
import com.ggang.be.domain.group.onceGroup.OnceGroupEntity;
import com.ggang.be.domain.user.UserEntity;
import com.ggang.be.global.annotation.Strategy;
import lombok.RequiredArgsConstructor;

@Strategy
@RequiredArgsConstructor
public class CancelOnceGroupStrategy implements CancelGroupStrategy {
    private final OnceGroupService onceGroupService;
    private final UserOnceGroupService userOnceGroupService;

    @Override
    public boolean support(GroupType groupType) {
        return groupType.equals(GroupType.ONCE);
    }

    @Override
    public void cancelGroup(UserEntity userEntity, GroupRequest request) {
        OnceGroupEntity onceGroupEntity = onceGroupService.findOnceGroupEntityByGroupId(request.groupId());
        if (onceGroupService.validateCancelOnceGroup(userEntity, onceGroupEntity))
            userOnceGroupService.cancelOnceGroup(userEntity, onceGroupEntity);
        else throw new GongBaekException(ResponseError.GROUP_CANCEL_NOT_FOUND);
    }
}
