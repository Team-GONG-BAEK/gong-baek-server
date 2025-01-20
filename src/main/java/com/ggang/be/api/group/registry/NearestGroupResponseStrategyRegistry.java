package com.ggang.be.api.group.registry;

import com.ggang.be.api.common.ResponseError;
import com.ggang.be.api.exception.GongBaekException;
import com.ggang.be.domain.group.vo.NearestGroup;
import com.ggang.be.global.annotation.Registry;
import java.util.List;
import lombok.RequiredArgsConstructor;

@Registry
@RequiredArgsConstructor
public class NearestGroupResponseStrategyRegistry {

    private final List<NearestGroupResponseStrategy> strategies;

    public NearestGroupResponseStrategy getStrategy(NearestGroup nearestOnceGroup,
        NearestGroup nearestEveryGroup) {
        return strategies.stream()
            .filter(strategy -> strategy.support(nearestEveryGroup, nearestOnceGroup))
            .findFirst()
            .orElseThrow(() -> new GongBaekException(ResponseError.BAD_REQUEST));
    }



}
