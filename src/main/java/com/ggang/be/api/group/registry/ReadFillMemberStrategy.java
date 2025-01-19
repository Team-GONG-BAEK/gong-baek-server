package com.ggang.be.api.group.registry;

import com.ggang.be.api.group.dto.ReadFillMembersRequest;
import com.ggang.be.api.group.dto.ReadFillMembersResponse;
import com.ggang.be.domain.constant.GroupType;
import com.ggang.be.domain.user.UserEntity;

public interface ReadFillMemberStrategy {

    boolean support(GroupType groupType);

    ReadFillMembersResponse getGroupUsersInfo(UserEntity findUserEntity, ReadFillMembersRequest dto);

}
