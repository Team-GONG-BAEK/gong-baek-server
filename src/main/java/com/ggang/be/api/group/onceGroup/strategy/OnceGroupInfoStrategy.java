package com.ggang.be.api.group.onceGroup.strategy;

import com.ggang.be.api.group.dto.GroupResponse;
import com.ggang.be.api.group.onceGroup.service.OnceGroupService;
import com.ggang.be.api.group.registry.GroupInfoStrategy;
import com.ggang.be.api.mapper.GroupResponseMapper;
import com.ggang.be.domain.common.SameSchoolValidator;
import com.ggang.be.domain.constant.GroupType;
import com.ggang.be.domain.group.onceGroup.OnceGroupEntity;
import com.ggang.be.domain.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OnceGroupInfoStrategy implements GroupInfoStrategy {

    private final OnceGroupService onceGroupService;
    private final SameSchoolValidator sameSchoolValidator;

    @Override
    public boolean supports(GroupType groupType) {
        return groupType == GroupType.ONCE;
    }

    @Override
    public GroupResponse getGroupInfo(Long groupId, UserEntity userEntity){
        OnceGroupEntity onceGroupEntity = onceGroupService.findOnceGroupEntityByGroupId(groupId);
        sameSchoolValidator.isUserReadMySchoolOnceGroup(userEntity, onceGroupEntity);
        return GroupResponseMapper.fromOnceGroup(onceGroupService.getOnceGroupDetail(groupId, userEntity));
    }
}
