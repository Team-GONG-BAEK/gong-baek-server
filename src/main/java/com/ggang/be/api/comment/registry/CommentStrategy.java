package com.ggang.be.api.comment.registry;

import com.ggang.be.api.comment.dto.*;
import com.ggang.be.domain.constant.GroupType;
import com.ggang.be.domain.user.UserEntity;

public interface CommentStrategy {

    ReadCommentResponse readComment(UserEntity findUserEntity, boolean isPublic, ReadCommentRequest dto);

    boolean supports(GroupType groupType);

    WriteCommentResponse writeComment(final long userId, WriteCommentRequest dto, WriteCommentEntityDto from);
}
