package com.ggang.be.api.group.registry;

import com.ggang.be.api.common.ResponseError;
import com.ggang.be.api.exception.GongBaekException;
import com.ggang.be.domain.constant.GroupType;
import com.ggang.be.global.annotation.Registry;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Registry
@RequiredArgsConstructor
public class RegisterGroupStrategyRegistry {

    private final List<RegisterGroupStrategy> registerGroupStrategies;

    public RegisterGroupStrategy getRegisterGroupStrategy(GroupType groupType) {
        return registerGroupStrategies.stream()
            .filter(strategy -> strategy.support(groupType))
            .findFirst()
            .orElseThrow(() -> new GongBaekException(ResponseError.BAD_REQUEST));
    }

}
