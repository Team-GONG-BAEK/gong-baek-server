package com.ggang.be.domain.comment.application;

import com.ggang.be.api.comment.dto.WriteCommentRequest;
import com.ggang.be.api.comment.service.CommentService;
import com.ggang.be.api.common.ResponseError;
import com.ggang.be.api.exception.GongBaekException;
import com.ggang.be.domain.comment.CommentEntity;
import com.ggang.be.domain.comment.infra.CommentRepository;
import com.ggang.be.domain.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    @Override
    @Transactional
    public CommentEntity writeComment(UserEntity findUser, WriteCommentRequest dto) {
        return commentRepository.save(CommentEntity.builder()
            .userEntity(findUser)
            .isPublic(dto.isPublic())
            .body(dto.body())
            .build());
    }

    @Override
    @Transactional
    public void deleteComment(final long commentId) {
        commentRepository.findById(commentId)
                .orElseThrow(() -> new GongBaekException(ResponseError.COMMENT_NOT_FOUND));

        commentRepository.deleteById(commentId);
    }

    @Override
    public CommentEntity findById(final long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new GongBaekException(ResponseError.COMMENT_NOT_FOUND));
    }
}
