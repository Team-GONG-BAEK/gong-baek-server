package com.ggang.be.api.group.registry;

import com.ggang.be.domain.constant.GroupType;
import com.ggang.be.domain.group.dto.GroupVo;
import com.ggang.be.domain.user.UserEntity;

import java.util.List;

public interface LatestGroupStrategy {
    boolean supports(GroupType groupType);

    List<GroupVo> getLatestGroups(UserEntity userEntity);
}
