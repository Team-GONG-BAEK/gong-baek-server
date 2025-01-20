package com.ggang.be.api.group.registry;

import com.ggang.be.api.group.dto.PrepareRegisterInfo;
import com.ggang.be.api.group.dto.RegisterGroupResponse;
import com.ggang.be.domain.constant.GroupType;

public interface RegisterGroupStrategy {

    boolean support(GroupType groupType);

    RegisterGroupResponse registerGroup(PrepareRegisterInfo prepareRegisterInfo);
}
