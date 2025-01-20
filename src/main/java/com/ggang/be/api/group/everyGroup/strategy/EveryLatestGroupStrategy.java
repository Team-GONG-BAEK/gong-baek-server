package com.ggang.be.api.group.everyGroup.strategy;

import com.ggang.be.api.group.everyGroup.service.EveryGroupService;
import com.ggang.be.api.group.registry.LatestGroupStrategy;
import com.ggang.be.domain.constant.GroupType;
import com.ggang.be.domain.group.dto.GroupVo;
import com.ggang.be.domain.group.everyGroup.EveryGroupEntity;
import com.ggang.be.domain.group.everyGroup.dto.EveryGroupVo;
import com.ggang.be.domain.user.UserEntity;
import com.ggang.be.global.annotation.Strategy;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Strategy
@RequiredArgsConstructor
public class EveryLatestGroupStrategy implements LatestGroupStrategy {

    private final EveryGroupService everyGroupService;

    @Override
    public boolean support(GroupType groupType) {
        return groupType == GroupType.WEEKLY;
    }

    @Override
    public List<GroupVo> getLatestGroups(UserEntity userEntity) {
        List<EveryGroupVo> everyGroupResponses = everyGroupService.getActiveEveryGroups(userEntity, null).groups();

        return everyGroupResponses.stream()
                .map(GroupVo::fromEveryGroup)
                .filter(groupVo -> isSameSchoolEveryGroup(userEntity, groupVo))
                .collect(Collectors.toList());
    }

    private boolean isSameSchoolEveryGroup(UserEntity currentUser, GroupVo groupVo) {
        String userSchool = currentUser.getSchool().getSchoolName();
        EveryGroupEntity everyGroupEntity = everyGroupService.findEveryGroupEntityByGroupId(groupVo.groupId());
        String groupCreatorSchool = everyGroupEntity.getUserEntity().getSchool().getSchoolName();

        return userSchool.equals(groupCreatorSchool);
    }
}

