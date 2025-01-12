package com.ggang.be.api.group.onceGroup.service;

import com.ggang.be.domain.comment.CommentEntity;
import com.ggang.be.domain.onceGroup.OnceGroupEntity;
import com.ggang.be.domain.onceGroup.dto.OnceGroupDto;
import com.ggang.be.domain.user.UserEntity;

public interface OnceGroupService {
    OnceGroupDto getOnceGroupDetail(final long groupId, UserEntity user);
    long getOnceGroupRegisterUserId(final long groupId);
    OnceGroupEntity findOnceGroupEntityByGroupId(long groupId);

    void writeCommentInGroup(CommentEntity commentEntity, final long groupId);
}
