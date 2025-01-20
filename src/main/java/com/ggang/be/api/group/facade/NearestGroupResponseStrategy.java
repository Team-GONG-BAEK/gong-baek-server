package com.ggang.be.api.group.facade;

import com.ggang.be.api.group.dto.NearestGroupResponse;

public interface NearestGroupResponseStrategy {

    boolean support(NearestGroup nearestEveryGroup, NearestGroup nearestOnceGroup);

    NearestGroupResponse getNearestGroupResponse(
        NearestGroup nearestOnceGroup, NearestGroup nearestEveryGroup);

}
