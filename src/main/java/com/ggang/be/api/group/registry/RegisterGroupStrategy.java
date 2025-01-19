package com.ggang.be.api.group.registry;

import com.ggang.be.api.group.dto.PrepareRegisterInfo;
import com.ggang.be.api.group.dto.RegisterGongbaekResponse;
import com.ggang.be.domain.constant.GroupType;

public interface RegisterGroupStrategy {

    boolean support(GroupType groupType);

    RegisterGongbaekResponse registerGroup(PrepareRegisterInfo prepareRegisterInfo);
}
