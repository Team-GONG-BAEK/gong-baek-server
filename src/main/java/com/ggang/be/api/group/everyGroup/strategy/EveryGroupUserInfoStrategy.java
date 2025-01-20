package com.ggang.be.api.group.everyGroup.strategy;

import com.ggang.be.api.group.everyGroup.service.EveryGroupService;
import com.ggang.be.api.group.registry.GroupUserInfoStrategy;
import com.ggang.be.domain.constant.GroupType;
import com.ggang.be.global.annotation.Strategy;
import lombok.RequiredArgsConstructor;

@Strategy
@RequiredArgsConstructor
public class EveryGroupUserInfoStrategy implements GroupUserInfoStrategy {

    private final EveryGroupService everyGroupService;

    @Override
    public boolean support(GroupType groupType) {
        return groupType == GroupType.WEEKLY;
    }

    @Override
    public long getGroupUserInfo(long groupId) {
        return everyGroupService.getEveryGroupRegisterUserId(groupId);
    }
}
