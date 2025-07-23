package com.ggang.be.api.group.everyGroup.strategy;

import com.ggang.be.api.common.ResponseError;
import com.ggang.be.api.exception.GongBaekException;
import com.ggang.be.api.group.dto.GroupRequest;
import com.ggang.be.api.group.everyGroup.service.EveryGroupService;
import com.ggang.be.api.group.registry.CancelGroupStrategy;
import com.ggang.be.api.group.validator.GroupValidator;
import com.ggang.be.api.userEveryGroup.service.UserEveryGroupService;
import com.ggang.be.domain.constant.GroupType;
import com.ggang.be.domain.group.everyGroup.EveryGroupEntity;
import com.ggang.be.domain.user.UserEntity;
import com.ggang.be.global.annotation.Strategy;
import lombok.RequiredArgsConstructor;

@Strategy
@RequiredArgsConstructor
public class CancelEveryGroupStrategy implements CancelGroupStrategy {
    private final EveryGroupService everyGroupService;
    private final UserEveryGroupService userEveryGroupService;
    private final GroupValidator groupValidator;

    @Override
    public boolean support(GroupType groupType) {
        return groupType.equals(GroupType.WEEKLY);
    }

    @Override
    public void cancelGroup(UserEntity userEntity, GroupRequest request) {
        EveryGroupEntity everyGroupEntity = everyGroupService.findEveryGroupEntityByGroupId(request.groupId());
        groupValidator.isValidEveryGroup(everyGroupEntity);
        if (everyGroupService.validateCancelEveryGroup(userEntity, everyGroupEntity))
            userEveryGroupService.cancelEveryGroup(userEntity, everyGroupEntity);
        else throw new GongBaekException(ResponseError.GROUP_CANCEL_NOT_FOUND);
    }

    @Override
    public boolean hasApplied(UserEntity userEntity, GroupRequest request) {
        EveryGroupEntity group = everyGroupService.findEveryGroupEntityByGroupId(request.groupId());
        return userEveryGroupService.hasApplied(userEntity, group);
    }
}
