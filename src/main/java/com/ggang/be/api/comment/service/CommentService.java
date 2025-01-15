package com.ggang.be.api.comment.service;

import com.ggang.be.api.comment.dto.WriteCommentRequest;
import com.ggang.be.domain.comment.CommentEntity;
import com.ggang.be.domain.user.UserEntity;

public interface CommentService {
    CommentEntity writeComment(UserEntity findUser, WriteCommentRequest dto);
}
