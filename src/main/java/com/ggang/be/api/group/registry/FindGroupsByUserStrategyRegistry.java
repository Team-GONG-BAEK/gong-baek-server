package com.ggang.be.api.group.registry;

import com.ggang.be.api.group.dto.GroupRequest;
import com.ggang.be.global.annotation.Registry;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Registry
@RequiredArgsConstructor
public class FindGroupsByUserStrategyRegistry {
    private final List<FindGroupsByUserStrategy> strategies;

    public List<GroupRequest> getAllGroupRequests(Long userId) {
        return strategies.stream()
                .flatMap(strategy -> strategy.toGroupRequests(userId).stream())
                .toList();
    }
}

