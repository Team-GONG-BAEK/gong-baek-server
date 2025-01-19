package com.ggang.be.api.comment.dto;

import com.ggang.be.domain.comment.CommentEntity;
import com.ggang.be.domain.user.UserEntity;

public record WriteCommentEntityDto(UserEntity userEntity, CommentEntity commentEntity) {

    public static WriteCommentEntityDto from(CommentEntity commentEntity,
        UserEntity findUserEntity) {
        return new WriteCommentEntityDto(findUserEntity, commentEntity);
    }
}
