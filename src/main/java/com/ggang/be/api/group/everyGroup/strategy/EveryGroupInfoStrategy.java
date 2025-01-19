package com.ggang.be.api.group.everyGroup.strategy;

import com.ggang.be.api.group.dto.GroupResponse;
import com.ggang.be.api.group.everyGroup.service.EveryGroupService;
import com.ggang.be.api.group.registry.GroupInfoStrategy;
import com.ggang.be.api.mapper.GroupResponseMapper;
import com.ggang.be.domain.common.SameSchoolValidator;
import com.ggang.be.domain.constant.GroupType;
import com.ggang.be.domain.group.everyGroup.EveryGroupEntity;
import com.ggang.be.domain.user.UserEntity;
import com.ggang.be.global.annotation.Strategy;
import lombok.RequiredArgsConstructor;

@Strategy
@RequiredArgsConstructor
public class EveryGroupInfoStrategy implements GroupInfoStrategy {

    private final EveryGroupService everyGroupService;
    private final SameSchoolValidator sameSchoolValidator;

    @Override
    public boolean supports(GroupType groupType) {
        return groupType == GroupType.WEEKLY;
    }

    @Override
    public GroupResponse getGroupInfo(Long groupId, UserEntity userEntity){
        EveryGroupEntity everyGroupEntity = everyGroupService.findEveryGroupEntityByGroupId(groupId);
        sameSchoolValidator.isUserReadMySchoolEveryGroup(userEntity, everyGroupEntity);
        return GroupResponseMapper.fromEveryGroup(everyGroupService.getEveryGroupDetail(groupId, userEntity));
    }
}
