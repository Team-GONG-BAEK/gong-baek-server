package com.ggang.be.api.group.registry;

import com.ggang.be.api.common.ResponseError;
import com.ggang.be.api.exception.GongBaekException;
import com.ggang.be.domain.constant.GroupType;
import com.ggang.be.global.annotation.Registry;
import java.util.List;
import lombok.RequiredArgsConstructor;

@Registry
@RequiredArgsConstructor
public class ApplyGroupStrategyRegistry {

    private final List<ApplyGroupStrategy> applyGroupStrategies;


    public ApplyGroupStrategy getApplyGroupStrategy(GroupType groupType){
        return applyGroupStrategies.stream()
                .filter(applyGroupStrategy -> applyGroupStrategy.support(groupType))
                .findFirst()
                .orElseThrow(() -> new GongBaekException(ResponseError.BAD_REQUEST));
    }




}
