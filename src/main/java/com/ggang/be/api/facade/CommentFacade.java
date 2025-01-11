package com.ggang.be.api.facade;

import com.ggang.be.api.comment.dto.ReadCommentRequest;
import com.ggang.be.api.comment.dto.ReadCommentResponse;
import com.ggang.be.api.comment.dto.WriteCommentRequest;
import com.ggang.be.api.comment.dto.WriteCommentResponse;
import com.ggang.be.api.comment.service.CommentService;
import com.ggang.be.api.common.ResponseError;
import com.ggang.be.api.exception.GongBaekException;
import com.ggang.be.api.group.everyGroup.service.EveryGroupService;
import com.ggang.be.api.group.onceGroup.service.OnceGroupService;
import com.ggang.be.api.user.service.UserService;
import com.ggang.be.domain.comment.CommentEntity;
import com.ggang.be.domain.user.UserEntity;
import com.ggang.be.domain.group.vo.ReadCommentGroup;
import com.ggang.be.global.annotation.Facade;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Facade
@RequiredArgsConstructor
public class CommentFacade {


    private final EveryGroupService everyGroupService;
    private final OnceGroupService onceGroupService;
    private final CommentService commentService;
    private final UserService userService;


    @Transactional
    public WriteCommentResponse writeComment(final long userId, WriteCommentRequest dto) {
        UserEntity findUser = userService.getUserById(userId);
        CommentEntity commentEntity = commentService.writeComment(findUser, dto);

        writeByCase(dto, commentEntity);

        return WriteCommentResponse.of(commentEntity.getId());
    }

    private void writeByCase(WriteCommentRequest dto, CommentEntity commentEntity) {
        if(dto.groupType() == GroupType.WEEKLY)
            everyGroupService.writeCommentInGroup(commentEntity, dto.groupId());
        if(dto.groupType() == GroupType.ONCE)
            onceGroupService.writeCommentInGroup(commentEntity, dto.groupId());
    }

    public ReadCommentResponse readComment(final boolean isPublic, ReadCommentRequest dto) {
        return ReadCommentResponse.of(readByCase(isPublic, dto));
    }

    private ReadCommentGroup readByCase(boolean isPublic, ReadCommentRequest dto) {
        if(dto.groupType() == GroupType.WEEKLY)
            return everyGroupService.readCommentInGroup(isPublic, dto.groupId());
        if(dto.groupType() == GroupType.ONCE)
            return onceGroupService.readCommentInGroup(isPublic, dto.groupId());
        throw new GongBaekException(ResponseError.BAD_REQUEST);
    }

}
