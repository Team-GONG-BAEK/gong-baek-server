package com.ggang.be.api.group.registry;

import com.ggang.be.api.group.dto.GroupRequest;
import com.ggang.be.domain.constant.GroupType;
import com.ggang.be.domain.user.UserEntity;

public interface ApplyGroupStrategy {

    boolean support(GroupType groupType);

    void applyGroup(UserEntity userEntity, GroupRequest request);

}
