package com.ggang.be.api.group.everyGroup.strategy;

import com.ggang.be.api.group.dto.GroupRequest;
import com.ggang.be.api.group.everyGroup.service.EveryGroupService;
import com.ggang.be.api.group.registry.FindGroupsByUserStrategy;
import com.ggang.be.domain.constant.GroupType;
import com.ggang.be.global.annotation.Strategy;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Strategy
@RequiredArgsConstructor
public class FindEveryGroupsByUserStrategy implements FindGroupsByUserStrategy {

    private final EveryGroupService everyGroupService;

    @Override
    public GroupType getGroupType() {
        return GroupType.WEEKLY;
    }

    @Override
    public List<GroupRequest> toGroupRequests(Long userId) {
        return everyGroupService.findByUserId(userId).stream()
                .map(group -> new GroupRequest(group.getId(), GroupType.WEEKLY))
                .toList();
    }
}
