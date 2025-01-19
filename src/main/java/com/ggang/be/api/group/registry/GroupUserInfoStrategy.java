package com.ggang.be.api.group.registry;

import com.ggang.be.domain.constant.GroupType;

public interface GroupUserInfoStrategy {
    boolean supports(GroupType groupType);

    long getGroupUserInfo(long groupId);
}