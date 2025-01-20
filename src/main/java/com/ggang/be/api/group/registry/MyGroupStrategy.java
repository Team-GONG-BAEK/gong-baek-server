package com.ggang.be.api.group.registry;

import com.ggang.be.domain.constant.FillGroupType;
import com.ggang.be.domain.group.dto.GroupVo;
import com.ggang.be.domain.user.UserEntity;

import java.util.List;

public interface MyGroupStrategy {
    boolean support(FillGroupType category);

    List<GroupVo> getGroups(UserEntity userEntity, boolean status);
}
