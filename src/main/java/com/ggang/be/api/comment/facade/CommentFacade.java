package com.ggang.be.api.comment.facade;

import com.ggang.be.api.comment.dto.ReadCommentRequest;
import com.ggang.be.api.comment.dto.ReadCommentResponse;
import com.ggang.be.api.comment.dto.WriteCommentEntityDto;
import com.ggang.be.api.comment.dto.WriteCommentRequest;
import com.ggang.be.api.comment.dto.WriteCommentResponse;
import com.ggang.be.api.comment.service.CommentService;
import com.ggang.be.api.comment.registry.CommentFacadeHandler;
import com.ggang.be.api.comment.registry.CommentRegistry;
import com.ggang.be.api.user.service.UserService;
import com.ggang.be.domain.comment.CommentEntity;
import com.ggang.be.domain.user.UserEntity;
import com.ggang.be.global.annotation.Facade;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Facade
@RequiredArgsConstructor
public class CommentFacade {

    private final CommentRegistry commentRegistry;
    private final UserService userService;
    private final CommentService commentService;

    @Transactional
    public WriteCommentResponse writeComment(final long userId, WriteCommentRequest dto) {

        CommentFacadeHandler commentGroupHandler = commentRegistry.getCommentGroupHandler(dto.groupType());

        UserEntity findUserEntity = userService.getUserById(userId);
        CommentEntity commentEntity = commentService.writeComment(findUserEntity, dto);

        return commentGroupHandler.writeComment(userId, dto, WriteCommentEntityDto.from(commentEntity, findUserEntity));

    }

    public ReadCommentResponse readComment(Long userId, final boolean isPublic,
        ReadCommentRequest dto) {

        UserEntity findUserEntity = userService.getUserById(userId);

        CommentFacadeHandler commentGroupHandler = commentRegistry.getCommentGroupHandler(dto.groupType());

        return commentGroupHandler.readComment(findUserEntity, isPublic, dto);
    }

}
