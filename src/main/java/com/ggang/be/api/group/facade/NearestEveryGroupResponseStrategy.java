package com.ggang.be.api.group.facade;

import com.ggang.be.api.group.dto.NearestGroupResponse;
import com.ggang.be.api.mapper.GroupResponseMapper;
import com.ggang.be.global.annotation.Strategy;
import lombok.RequiredArgsConstructor;

@Strategy
@RequiredArgsConstructor
public class NearestEveryGroupResponseStrategy implements NearestGroupResponseStrategy {

    @Override
    public boolean support(NearestGroup nearestEveryGroup, NearestGroup nearestOnceGroup) {
        return nearestEveryGroup != null && nearestOnceGroup == null;
    }

    @Override
    public NearestGroupResponse getNearestGroupResponse(
        NearestGroup nearestOnceGroup,
        NearestGroup nearestEveryGroup) {
        return GroupResponseMapper.toNearestGroupResponse(nearestEveryGroup);
    }

}
