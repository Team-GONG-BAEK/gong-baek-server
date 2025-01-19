package com.ggang.be.api.group.registry;

import com.ggang.be.api.group.dto.GroupResponse;
import com.ggang.be.domain.constant.GroupType;
import com.ggang.be.domain.user.UserEntity;

public interface GroupInfoStrategy {
    boolean supports(GroupType groupType);

    GroupResponse getGroupInfo(Long groupId, UserEntity userEntity);
}
