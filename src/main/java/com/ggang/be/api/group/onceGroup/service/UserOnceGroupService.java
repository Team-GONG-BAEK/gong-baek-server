package com.ggang.be.api.group.onceGroup.service;

import com.ggang.be.domain.group.dto.ReadOnceGroupMember;
import com.ggang.be.domain.userEveryGroup.dto.FillMember;
import java.util.List;

public interface UserOnceGroupService {
    List<FillMember> getOnceGroupUsersInfo(ReadOnceGroupMember dto);
}
