package com.ggang.be.api.group.registry;

import com.ggang.be.api.group.dto.NearestGroupResponse;
import com.ggang.be.domain.group.vo.NearestGroup;

public interface NearestGroupResponseStrategy {

    boolean support(NearestGroup nearestEveryGroup, NearestGroup nearestOnceGroup);

    NearestGroupResponse getNearestGroupResponse(
        NearestGroup nearestOnceGroup, NearestGroup nearestEveryGroup);

}
