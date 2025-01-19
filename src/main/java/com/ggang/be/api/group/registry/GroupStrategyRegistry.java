package com.ggang.be.api.group.registry;

import com.ggang.be.api.common.ResponseError;
import com.ggang.be.api.exception.GongBaekException;
import com.ggang.be.domain.constant.GroupType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GroupStrategyRegistry {

    private final List<LatestGroupStrategy> groupStrategies;

    public LatestGroupStrategy getGroupStrategy(GroupType groupType) {
        return groupStrategies.stream()
                .filter(strategy -> strategy.supports(groupType))
                .findFirst()
                .orElseThrow(() -> new GongBaekException(ResponseError.BAD_REQUEST));
    }
}
