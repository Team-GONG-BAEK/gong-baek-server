package com.ggang.be.api.group.facade;

import com.ggang.be.api.group.dto.NearestGroupResponse;
import com.ggang.be.api.mapper.GroupResponseMapper;
import com.ggang.be.global.annotation.Strategy;
import java.time.LocalDate;
import lombok.extern.slf4j.Slf4j;

@Strategy
@Slf4j
public class NearestBothGroupResponseStrategy implements NearestGroupResponseStrategy {

    @Override
    public boolean support(NearestGroup nearestEveryGroup, NearestGroup nearestOnceGroup) {
        return nearestEveryGroup != null && nearestOnceGroup != null;
    }

    @Override
    public NearestGroupResponse getNearestGroupResponse(
        NearestGroup nearestOnceGroup,
        NearestGroup nearestEveryGroup) {
        LocalDate everyGroupDate = nearestEveryGroup.weekDate();
        LocalDate onceGroupDate = nearestOnceGroup.weekDate();

        log.info("EveryGroup Date: {}", everyGroupDate);
        log.info("OnceGroup Date: {}", onceGroupDate);

        if (everyGroupDate.isBefore(onceGroupDate))
            return GroupResponseMapper.toNearestGroupResponse(nearestEveryGroup);

        return GroupResponseMapper.toNearestGroupResponse(nearestOnceGroup);
    }

}
