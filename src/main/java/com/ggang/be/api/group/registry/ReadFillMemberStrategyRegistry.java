package com.ggang.be.api.group.registry;

import com.ggang.be.api.common.ResponseError;
import com.ggang.be.api.exception.GongBaekException;
import com.ggang.be.domain.constant.GroupType;
import com.ggang.be.global.annotation.Registry;
import java.util.List;
import lombok.RequiredArgsConstructor;

@Registry
@RequiredArgsConstructor
public class ReadFillMemberStrategyRegistry {

    private final List<ReadFillMemberStrategy> strategies;


    public ReadFillMemberStrategy getStrategy(GroupType groupType){
        return strategies.stream()
            .filter(strategy -> strategy.support(groupType))
            .findFirst().orElseThrow(() -> new GongBaekException(ResponseError.BAD_REQUEST));
    }

}
