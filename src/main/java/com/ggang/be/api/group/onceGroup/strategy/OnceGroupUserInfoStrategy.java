package com.ggang.be.api.group.onceGroup.strategy;

import com.ggang.be.api.group.onceGroup.service.OnceGroupService;
import com.ggang.be.api.group.registry.GroupUserInfoStrategy;
import com.ggang.be.domain.constant.GroupType;
import com.ggang.be.global.annotation.Strategy;
import lombok.RequiredArgsConstructor;

@Strategy
@RequiredArgsConstructor
public class OnceGroupUserInfoStrategy implements GroupUserInfoStrategy {

    private final OnceGroupService onceGroupService;

    @Override
    public boolean support(GroupType groupType) {
        return groupType == GroupType.ONCE;
    }

    @Override
    public long getGroupUserInfo(long groupId) {
        return onceGroupService.getOnceGroupRegisterUserId(groupId);
    }
}
