package com.ggang.be.api.group.facade;

import com.ggang.be.api.group.dto.GroupResponsesDto;
import com.ggang.be.api.group.everyGroup.service.EveryGroupService;
import com.ggang.be.api.group.onceGroup.service.OnceGroupService;
import com.ggang.be.domain.constant.Category;
import com.ggang.be.domain.group.everyGroup.dto.EveryGroupVo;
import com.ggang.be.domain.group.onceGroup.dto.OnceGroupVo;
import com.ggang.be.domain.user.UserEntity;
import com.ggang.be.global.annotation.Facade;
import java.util.List;
import lombok.RequiredArgsConstructor;

@Facade
@RequiredArgsConstructor
public class PrepareGroupResponsesFacade {

    private final EveryGroupService everyGroupService;
    private final OnceGroupService onceGroupService;

    public GroupResponsesDto prepareGroupResponses(UserEntity currentUser, Category category){
        List<EveryGroupVo> everyGroupResponses = everyGroupService.getActiveEveryGroups(currentUser, category).groups();
        List<OnceGroupVo> onceGroupResponses = onceGroupService.getActiveOnceGroups(currentUser, category).groups();

        return GroupResponsesDto.of(everyGroupResponses, onceGroupResponses);
    }

}
