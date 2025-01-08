package com.ggang.be.api.facade;

import com.ggang.be.api.common.GroupResponse;
import com.ggang.be.api.common.ResponseError;
import com.ggang.be.api.exception.GongBaekException;
import com.ggang.be.domain.everyGroup.EveryGroupService;
import com.ggang.be.domain.onceGroup.OnceGroupService;
import com.ggang.be.domain.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupFacade {

    private final EveryGroupService everyGroupService;
    private final OnceGroupService onceGroupService;

    public GroupResponse getGroupInfo(String groupType, Long groupId, UserEntity user) {
        return switch (groupType.toUpperCase()) {
            case "WEEKLY" -> everyGroupService.getEveryGroupDetail(groupId, user);
            case "ONCE" -> onceGroupService.getOnceGroupDetail(groupId, user);
            default -> throw new GongBaekException(ResponseError.BAD_REQUEST);
        };
    }
}
