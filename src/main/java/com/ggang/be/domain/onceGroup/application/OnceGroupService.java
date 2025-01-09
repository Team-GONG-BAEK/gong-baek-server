package com.ggang.be.domain.onceGroup.application;

import com.ggang.be.api.common.GroupResponse;
import com.ggang.be.api.common.ResponseError;
import com.ggang.be.api.exception.GongBaekException;
import com.ggang.be.api.mapper.GroupResponseMapper;
import com.ggang.be.domain.onceGroup.OnceGroupEntity;
import com.ggang.be.domain.onceGroup.infra.OnceGroupRepository;
import com.ggang.be.domain.user.UserEntity;
import org.springframework.stereotype.Service;

@Service
public class OnceGroupService {
    private final OnceGroupRepository onceGroupRepository;

    public OnceGroupService(OnceGroupRepository onceGroupRepository) {
        this.onceGroupRepository = onceGroupRepository;
    }

    public GroupResponse getOnceGroupDetail(final long groupId, UserEntity currentUser) {
        OnceGroupEntity onceGroupEntity = findIdOrThrow(groupId);

        return GroupResponseMapper.fromOnceGroup(onceGroupEntity, currentUser);
    }

    private OnceGroupEntity findIdOrThrow(final long groupId){
        return onceGroupRepository.findById(groupId).orElseThrow(
                () -> new GongBaekException(ResponseError.GROUP_NOT_FOUND)
        );
    }
}
