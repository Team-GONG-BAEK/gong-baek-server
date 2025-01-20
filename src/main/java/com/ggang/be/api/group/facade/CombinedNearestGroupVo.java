package com.ggang.be.api.group.facade;

import com.ggang.be.domain.group.vo.NearestGroup;

public record CombinedNearestGroupVo(NearestGroup nearestEveryGroup, NearestGroup nearestOnceGroup) {

    public static CombinedNearestGroupVo of(NearestGroup nearestEveryGroup, NearestGroup nearestOnceGroup) {
        return new CombinedNearestGroupVo(nearestEveryGroup, nearestOnceGroup);
    }

}
