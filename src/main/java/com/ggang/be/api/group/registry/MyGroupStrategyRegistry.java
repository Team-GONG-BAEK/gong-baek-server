package com.ggang.be.api.group.registry;

import com.ggang.be.api.common.ResponseError;
import com.ggang.be.api.exception.GongBaekException;
import com.ggang.be.domain.constant.FillGroupType;
import com.ggang.be.global.annotation.Registry;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Registry
@RequiredArgsConstructor
public class MyGroupStrategyRegistry {

    private final List<MyGroupStrategy> groupStrategies;

    public MyGroupStrategy getGroupStrategy(FillGroupType category) {
        return groupStrategies.stream()
                .filter(strategy -> strategy.supports(category))
                .findFirst()
                .orElseThrow(() -> new GongBaekException(ResponseError.BAD_REQUEST));
    }
}
