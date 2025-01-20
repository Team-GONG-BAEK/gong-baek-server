package com.ggang.be.api.group.onceGroup.strategy;

import com.ggang.be.api.group.dto.NearestGroupResponse;
import com.ggang.be.domain.group.vo.NearestGroup;
import com.ggang.be.api.group.registry.NearestGroupResponseStrategy;
import com.ggang.be.api.mapper.GroupResponseMapper;
import com.ggang.be.global.annotation.Strategy;

@Strategy
public class NearestOnceGroupResponseStrategy implements NearestGroupResponseStrategy {

    @Override
    public boolean support(NearestGroup nearestEveryGroup, NearestGroup nearestOnceGroup) {
        return nearestEveryGroup == null && nearestOnceGroup != null;
    }

    @Override
    public NearestGroupResponse getNearestGroupResponse(
        NearestGroup nearestOnceGroup,
        NearestGroup nearestEveryGroup) {
        return GroupResponseMapper.toNearestGroupResponse(nearestOnceGroup);
    }


}
