package com.ggang.be.api.facade;

import com.ggang.be.api.group.dto.GroupResponseDto;
import com.ggang.be.api.group.everyGroup.service.EveryGroupService;
import com.ggang.be.api.group.onceGroup.service.OnceGroupService;
import com.ggang.be.api.mapper.GroupResponseMapper;
import com.ggang.be.domain.user.UserEntity;
import com.ggang.be.domain.user.application.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupFacade {

    private final EveryGroupService everyGroupService;
    private final OnceGroupService onceGroupService;
    private final UserService userService;

    public GroupResponseDto getGroupInfo(GroupType groupType, Long groupId, String accessToken) {
        UserEntity currentUser = userService.getUserById(Long.parseLong(accessToken));

        return switch (groupType) {
            case WEEKLY -> GroupResponseMapper.fromEveryGroup(
                    everyGroupService.getEveryGroupDetail(groupId, currentUser)
            );
            case ONCE -> GroupResponseMapper.fromOnceGroup(
                    onceGroupService.getOnceGroupDetail(groupId, currentUser)
            );
        };
    }
}
