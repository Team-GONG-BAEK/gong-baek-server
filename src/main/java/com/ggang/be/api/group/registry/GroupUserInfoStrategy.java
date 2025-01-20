package com.ggang.be.api.group.registry;

import com.ggang.be.domain.constant.GroupType;

public interface GroupUserInfoStrategy {
    boolean support(GroupType groupType);

    long getGroupUserInfo(long groupId);
}