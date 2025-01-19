package com.ggang.be.api.group.strategy;

import com.ggang.be.api.group.registry.MyGroupStrategy;
import com.ggang.be.api.userEveryGroup.service.UserEveryGroupService;
import com.ggang.be.api.userOnceGroup.service.UserOnceGroupService;
import com.ggang.be.domain.constant.FillGroupType;
import com.ggang.be.domain.group.dto.GroupVo;
import com.ggang.be.domain.group.everyGroup.dto.EveryGroupVo;
import com.ggang.be.domain.group.onceGroup.dto.OnceGroupVo;
import com.ggang.be.domain.user.UserEntity;
import com.ggang.be.global.util.GroupVoAggregator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MyApplyGroupStrategy implements MyGroupStrategy {

    private final UserEveryGroupService userEveryGroupService;
    private final UserOnceGroupService userOnceGroupService;

    @Override
    public boolean supports(FillGroupType category) {
        return category == FillGroupType.APPLY;
    }

    @Override
    public List<GroupVo> getGroups(UserEntity userEntity, boolean status) {
        List<EveryGroupVo> everyGroupResponses = userEveryGroupService.getMyAppliedGroups(userEntity, status).groups();
        List<OnceGroupVo> onceGroupResponses = userOnceGroupService.getMyAppliedGroups(userEntity, status).groups();

        return GroupVoAggregator.aggregateAndSort(everyGroupResponses, onceGroupResponses);
    }
}
