package com.ggang.be.api.group.facade;

import com.ggang.be.api.userEveryGroup.service.UserEveryGroupService;
import com.ggang.be.api.userOnceGroup.service.UserOnceGroupService;
import com.ggang.be.domain.group.vo.NearestGroup;
import com.ggang.be.domain.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CombinedNearestGroupVoPreparer {

    private final UserEveryGroupService userEveryGroupService;
    private final UserOnceGroupService userOnceGroupService;

    public CombinedNearestGroupVo prepare(UserEntity currentUser) {
        NearestGroup nearestEveryGroup = userEveryGroupService.getMyNearestGroup(
            currentUser);
        NearestGroup nearestOnceGroup = userOnceGroupService.getMyNearestGroup(currentUser);

        return CombinedNearestGroupVo.of(nearestEveryGroup, nearestOnceGroup);
    }


}
