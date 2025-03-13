package com.ggang.be.api.comment.strategy;

import com.ggang.be.api.comment.dto.*;
import com.ggang.be.api.comment.registry.CommentStrategy;
import com.ggang.be.api.group.onceGroup.service.OnceGroupService;
import com.ggang.be.api.userOnceGroup.service.UserOnceGroupService;
import com.ggang.be.domain.comment.CommentEntity;
import com.ggang.be.domain.common.SameSchoolValidator;
import com.ggang.be.domain.constant.GroupType;
import com.ggang.be.domain.group.onceGroup.OnceGroupEntity;
import com.ggang.be.domain.user.UserEntity;
import com.ggang.be.global.annotation.Strategy;
import lombok.RequiredArgsConstructor;

@Strategy
@RequiredArgsConstructor
public class OnceGroupCommentStrategy implements CommentStrategy {

    private final OnceGroupService onceGroupService;
    private final SameSchoolValidator sameSchoolValidator;
    private final UserOnceGroupService userOnceGroupService;

    @Override
    public boolean supports(GroupType groupType) {
        return groupType.equals(GroupType.ONCE);
    }

    @Override
    public WriteCommentResponse writeComment(
            long userId, WriteCommentRequest dto, WriteCommentEntityDto entityDto
    ) {

        UserEntity findUserEntity = entityDto.userEntity();
        CommentEntity commentEntity = entityDto.commentEntity();
        OnceGroupEntity onceGroupEntity = onceGroupService.findOnceGroupEntityByGroupId(dto.groupId());

        sameSchoolValidator.isUserReadMySchoolOnceGroup(commentEntity.getUserEntity(), onceGroupEntity);
        isUserAccessOnceGroupComment(findUserEntity, dto.isPublic(), dto.groupId());
        onceGroupService.writeCommentInGroup(commentEntity, dto.groupId());

        return WriteCommentResponse.of(commentEntity.getId());
    }

    @Override
    public void deleteComment(UserEntity findUserEntity, DeleteCommentRequest dto) {
        OnceGroupEntity onceGroup = onceGroupService.findOnceGroupEntityByGroupId(dto.groupId());
        CommentEntity comment = onceGroup.getComments().stream()
                .filter(c -> c.getId().equals(dto.commentId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));

        onceGroupService.deleteComment(findUserEntity, comment, dto.groupId());
    }

    @Override
    public ReadCommentResponse readComment(
            UserEntity findUserEntity, boolean isPublic, ReadCommentRequest dto
    ) {
        OnceGroupEntity onceGroupEntity = onceGroupService.findOnceGroupEntityByGroupId(dto.groupId());
        sameSchoolValidator.isUserReadMySchoolOnceGroup(findUserEntity, onceGroupEntity);
        isUserAccessOnceGroupComment(findUserEntity, isPublic, dto.groupId());

        return ReadCommentResponse.of(onceGroupService.readCommentInGroup(findUserEntity, isPublic, dto.groupId()));
    }

    private void isUserAccessOnceGroupComment(UserEntity userEntity, boolean isPublic, long groupId) {
        if (!isPublic) userOnceGroupService.isValidCommentAccess(userEntity, groupId);
    }
}
