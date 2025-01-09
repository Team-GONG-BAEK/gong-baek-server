package com.ggang.be.api.facade;

import com.ggang.be.api.common.GroupResponse;
import com.ggang.be.api.common.ResponseError;
import com.ggang.be.api.exception.GongBaekException;
import com.ggang.be.domain.everyGroup.application.EveryGroupService;
import com.ggang.be.domain.onceGroup.application.OnceGroupService;
import com.ggang.be.domain.user.UserEntity;
import com.ggang.be.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupFacade {

    private final EveryGroupService everyGroupService;
    private final OnceGroupService onceGroupService;
    private final UserService userService;

    public GroupResponse getGroupInfo(String groupType, Long groupId, String accessToken) {
        UserEntity userInfo = userService.getUserById(Long.parseLong(accessToken));

        return switch (groupType.toUpperCase()) {
            case "WEEKLY" -> everyGroupService.getEveryGroupDetail(groupId, userInfo);
            case "ONCE" -> onceGroupService.getOnceGroupDetail(groupId, userInfo);
            default -> throw new GongBaekException(ResponseError.BAD_REQUEST);
        };
    }
}
