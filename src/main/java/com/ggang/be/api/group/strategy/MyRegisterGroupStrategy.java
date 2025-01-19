package com.ggang.be.api.group.strategy;

import com.ggang.be.api.group.GroupVoAggregator;
import com.ggang.be.api.group.everyGroup.service.EveryGroupService;
import com.ggang.be.api.group.onceGroup.service.OnceGroupService;
import com.ggang.be.api.group.registry.MyGroupStrategy;
import com.ggang.be.domain.constant.FillGroupType;
import com.ggang.be.domain.group.dto.GroupVo;
import com.ggang.be.domain.group.everyGroup.dto.EveryGroupVo;
import com.ggang.be.domain.group.onceGroup.dto.OnceGroupVo;
import com.ggang.be.domain.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MyRegisterGroupStrategy implements MyGroupStrategy {

    private final EveryGroupService everyGroupService;
    private final OnceGroupService onceGroupService;

    @Override
    public boolean supports(FillGroupType category) {
        return category == FillGroupType.REGISTER;
    }

    @Override
    public List<GroupVo> getGroups(UserEntity userEntity, boolean status) {
        List<EveryGroupVo> everyGroupResponses = everyGroupService.getMyRegisteredGroups(userEntity, status).groups();
        List<OnceGroupVo> onceGroupResponses = onceGroupService.getMyRegisteredGroups(userEntity, status).groups();

        return GroupVoAggregator.aggregateAndSort(everyGroupResponses, onceGroupResponses);
    }
}
