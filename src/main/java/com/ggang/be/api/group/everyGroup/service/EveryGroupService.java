package com.ggang.be.api.group.everyGroup.service;

import com.ggang.be.domain.comment.CommentEntity;
import com.ggang.be.domain.everyGroup.EveryGroupEntity;
import com.ggang.be.domain.everyGroup.dto.EveryGroupDto;
import com.ggang.be.domain.user.UserEntity;

public interface EveryGroupService {
    EveryGroupDto getEveryGroupDetail(final long groupId, UserEntity userEntity);
    long getEveryGroupRegisterUserId(final long groupId);
    EveryGroupEntity findEveryGroupEntityByGroupId(long groupId);

    void writeCommentInGroup(CommentEntity commentEntity, final long groupId);
}
