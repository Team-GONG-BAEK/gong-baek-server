package com.ggang.be.api.group.onceGroup.strategy;

import com.ggang.be.api.group.dto.GroupRequest;
import com.ggang.be.api.group.onceGroup.service.OnceGroupService;
import com.ggang.be.api.group.registry.FindGroupsByUserStrategy;
import com.ggang.be.domain.constant.GroupType;
import com.ggang.be.global.annotation.Strategy;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Strategy
@RequiredArgsConstructor
public class FindOnceGroupsByUserStrategy implements FindGroupsByUserStrategy {

    private final OnceGroupService onceGroupService;

    @Override
    public GroupType getGroupType() {
        return GroupType.ONCE;
    }

    @Override
    public List<GroupRequest> toGroupRequests(Long userId) {
        return onceGroupService.findByUserId(userId).stream()
                .map(group -> new GroupRequest(group.getId(), GroupType.ONCE))
                .toList();
    }
}
