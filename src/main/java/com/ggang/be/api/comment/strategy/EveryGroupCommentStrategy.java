package com.ggang.be.api.comment.strategy;

import com.ggang.be.api.comment.dto.*;
import com.ggang.be.api.comment.registry.CommentStrategy;
import com.ggang.be.api.group.everyGroup.service.EveryGroupService;
import com.ggang.be.api.userEveryGroup.service.UserEveryGroupService;
import com.ggang.be.domain.comment.CommentEntity;
import com.ggang.be.domain.common.SameSchoolValidator;
import com.ggang.be.domain.constant.GroupType;
import com.ggang.be.domain.group.everyGroup.EveryGroupEntity;
import com.ggang.be.domain.user.UserEntity;
import com.ggang.be.global.annotation.Strategy;
import lombok.RequiredArgsConstructor;

@Strategy
@RequiredArgsConstructor
public class EveryGroupCommentStrategy implements CommentStrategy {

    private final EveryGroupService everyGroupService;
    private final SameSchoolValidator sameSchoolValidator;
    private final UserEveryGroupService userEveryGroupService;

    @Override
    public WriteCommentResponse writeComment(long userId, WriteCommentRequest dto, WriteCommentEntityDto from) {
        CommentEntity commentEntity = from.commentEntity();
        UserEntity findUserEntity = from.userEntity();

        EveryGroupEntity everyGroupEntity = everyGroupService.findEveryGroupEntityByGroupId(dto.groupId());

        sameSchoolValidator.isUserReadMySchoolEveryGroup(findUserEntity, everyGroupEntity);
        isUserAccessEveryGroupComment(findUserEntity, dto.isPublic(), dto.groupId());
        everyGroupService.writeCommentInGroup(commentEntity, dto.groupId());

        return WriteCommentResponse.of(commentEntity.getId());
    }

    @Override
    public void deleteComment(UserEntity findUserEntity, DeleteCommentRequest dto) {
        EveryGroupEntity everyGroup = everyGroupService.findEveryGroupEntityByGroupId(dto.groupId());
        CommentEntity comment = everyGroup.getComments().stream()
                .filter(c -> c.getId().equals(dto.commentId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));

        // everyGroup.getComments().remove(comment);
        everyGroupService.deleteComment(findUserEntity, comment, dto.groupId());
    }
    @Override
    public ReadCommentResponse readComment(UserEntity findUserEntity, boolean isPublic, ReadCommentRequest dto) {
        EveryGroupEntity everyGroupEntity = everyGroupService.findEveryGroupEntityByGroupId(dto.groupId());
        sameSchoolValidator.isUserReadMySchoolEveryGroup(findUserEntity, everyGroupEntity);
        isUserAccessEveryGroupComment(findUserEntity, isPublic, dto.groupId());

        return ReadCommentResponse.of(everyGroupService.readCommentInGroup(findUserEntity, isPublic, dto.groupId()));
    }

    @Override
    public boolean supports(GroupType groupType) {
        return groupType.equals(GroupType.WEEKLY);
    }

    private void isUserAccessEveryGroupComment(UserEntity userEntity, boolean isPublic, long groupId) {
        if (!isPublic) userEveryGroupService.isValidCommentAccess(userEntity, groupId);
    }
}
