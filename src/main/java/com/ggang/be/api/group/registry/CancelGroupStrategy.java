package com.ggang.be.api.group.registry;

import com.ggang.be.api.group.dto.GroupRequest;
import com.ggang.be.domain.constant.GroupType;
import com.ggang.be.domain.user.UserEntity;

public interface CancelGroupStrategy {
    boolean support(GroupType groupType);

    void cancelGroup(UserEntity userEntity, GroupRequest request);

    boolean hasApplied(UserEntity userEntity, GroupRequest request);
}
