package com.ggang.be.api.group.registry;

import com.ggang.be.api.group.dto.GroupRequest;
import com.ggang.be.domain.constant.GroupType;

import java.util.List;

public interface FindGroupsByUserStrategy {
    GroupType getGroupType();

    List<GroupRequest> toGroupRequests(Long userId);
}
