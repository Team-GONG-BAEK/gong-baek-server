package com.ggang.be.domain.everyGroup.application;

import com.ggang.be.api.common.GroupResponse;
import com.ggang.be.api.common.ResponseError;
import com.ggang.be.api.exception.GongBaekException;
import com.ggang.be.api.mapper.GroupResponseMapper;
import com.ggang.be.domain.everyGroup.EveryGroupEntity;
import com.ggang.be.domain.everyGroup.infra.EveryGroupRepository;
import com.ggang.be.domain.user.UserEntity;
import org.springframework.stereotype.Service;

@Service
public class EveryGroupService {
    private final EveryGroupRepository everyGroupRepository;

    public EveryGroupService(EveryGroupRepository everyGroupRepository) {
        this.everyGroupRepository = everyGroupRepository;
    }

    public GroupResponse getEveryGroupDetail(final long groupId, UserEntity currentUser) {
        EveryGroupEntity everyGroupEntity = findIdOrThrow(groupId);

        return GroupResponseMapper.fromEveryGroup(everyGroupEntity, currentUser);
    }

    private EveryGroupEntity findIdOrThrow(final long groupId){
        return everyGroupRepository.findById(groupId).orElseThrow(
                () -> new GongBaekException(ResponseError.GROUP_NOT_FOUND)
        );
    }
}
