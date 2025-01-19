package com.ggang.be.api.group.registry;

import com.ggang.be.api.common.ResponseError;
import com.ggang.be.api.exception.GongBaekException;
import com.ggang.be.domain.constant.GroupType;
import com.ggang.be.global.annotation.Registry;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Registry
@RequiredArgsConstructor
public class GroupInfoStrategyRegistry {

    private final List<GroupInfoStrategy> groupStrategies;

    public GroupInfoStrategy getGroupInfo(GroupType groupType) {
        return groupStrategies.stream()
                .filter(strategy -> strategy.supports(groupType))
                .findFirst()
                .orElseThrow(() -> new GongBaekException(ResponseError.BAD_REQUEST));
    }
}
