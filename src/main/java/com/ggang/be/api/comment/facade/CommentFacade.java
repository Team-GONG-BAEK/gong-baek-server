package com.ggang.be.api.comment.facade;

import com.ggang.be.api.comment.dto.ReadCommentRequest;
import com.ggang.be.api.comment.dto.ReadCommentResponse;
import com.ggang.be.api.comment.dto.WriteCommentEntityDto;
import com.ggang.be.api.comment.dto.WriteCommentRequest;
import com.ggang.be.api.comment.dto.WriteCommentResponse;
import com.ggang.be.api.comment.service.CommentService;
import com.ggang.be.api.comment.registry.CommentStrategy;
import com.ggang.be.api.comment.registry.CommentStrategyRegistry;
import com.ggang.be.api.user.service.UserService;
import com.ggang.be.domain.comment.CommentEntity;
import com.ggang.be.domain.user.UserEntity;
import com.ggang.be.global.annotation.Facade;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Facade
@RequiredArgsConstructor
public class CommentFacade {

    private final CommentStrategyRegistry commentStrategyRegistry;
    private final UserService userService;
    private final CommentService commentService;

    @Transactional
    public WriteCommentResponse writeComment(final long userId, WriteCommentRequest dto) {

        CommentStrategy commentStrategy = commentStrategyRegistry.getCommentGroupStrategy(dto.groupType());

        UserEntity findUserEntity = userService.getUserById(userId);
        CommentEntity commentEntity = commentService.writeComment(findUserEntity, dto);

        return commentStrategy.writeComment(userId, dto, WriteCommentEntityDto.from(commentEntity, findUserEntity));

    }

    public ReadCommentResponse readComment(Long userId, final boolean isPublic,
        ReadCommentRequest dto) {

        CommentStrategy commentStrategy = commentStrategyRegistry.getCommentGroupStrategy(dto.groupType());


        UserEntity findUserEntity = userService.getUserById(userId);


        return commentStrategy.readComment(findUserEntity, isPublic, dto);
    }

}
