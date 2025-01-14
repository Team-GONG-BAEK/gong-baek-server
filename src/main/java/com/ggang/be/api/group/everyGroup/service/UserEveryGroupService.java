package com.ggang.be.api.group.everyGroup.service;

import com.ggang.be.domain.group.dto.ReadEveryGroupMember;
import com.ggang.be.domain.userEveryGroup.dto.FillMember;
import java.util.List;

public interface UserEveryGroupService {
    List<FillMember> getEveryGroupUsersInfo(ReadEveryGroupMember dto);

}
