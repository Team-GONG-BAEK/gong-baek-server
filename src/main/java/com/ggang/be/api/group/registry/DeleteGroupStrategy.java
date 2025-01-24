package com.ggang.be.api.group.registry;

import com.ggang.be.api.group.dto.GroupRequest;
import com.ggang.be.domain.constant.GroupType;
import com.ggang.be.domain.user.UserEntity;

public interface DeleteGroupStrategy {
    boolean support(GroupType groupType);

    void deleteGroup(UserEntity userEntity, GroupRequest request);
}
