package com.ggang.be.api.comment.registry;

import com.ggang.be.api.comment.dto.ReadCommentRequest;
import com.ggang.be.api.comment.dto.ReadCommentResponse;
import com.ggang.be.api.comment.dto.WriteCommentEntityDto;
import com.ggang.be.api.comment.dto.WriteCommentRequest;
import com.ggang.be.api.comment.dto.WriteCommentResponse;
import com.ggang.be.domain.constant.GroupType;
import com.ggang.be.domain.user.UserEntity;

public interface CommentStrategy {

    ReadCommentResponse readComment(UserEntity findUserEntity, boolean isPublic,
        ReadCommentRequest dto);

    boolean supports(GroupType groupType);

    WriteCommentResponse writeComment(final long userId, WriteCommentRequest dto,
        WriteCommentEntityDto from);


}
