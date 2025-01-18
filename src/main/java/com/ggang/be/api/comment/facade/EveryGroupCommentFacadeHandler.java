package com.ggang.be.api.comment.facade;

import com.ggang.be.api.comment.dto.ReadCommentRequest;
import com.ggang.be.api.comment.dto.ReadCommentResponse;
import com.ggang.be.api.comment.dto.WriteCommentEntityDto;
import com.ggang.be.api.comment.dto.WriteCommentRequest;
import com.ggang.be.api.comment.dto.WriteCommentResponse;
import com.ggang.be.api.comment.registry.CommentFacadeHandler;
import com.ggang.be.api.group.everyGroup.service.EveryGroupService;
import com.ggang.be.api.userEveryGroup.service.UserEveryGroupService;
import com.ggang.be.domain.comment.CommentEntity;
import com.ggang.be.domain.common.SameSchoolValidator;
import com.ggang.be.domain.constant.GroupType;
import com.ggang.be.domain.group.everyGroup.EveryGroupEntity;
import com.ggang.be.domain.user.UserEntity;
import com.ggang.be.global.annotation.Facade;
import lombok.RequiredArgsConstructor;

@Facade
@RequiredArgsConstructor
public class EveryGroupCommentFacadeHandler implements CommentFacadeHandler {

    private final EveryGroupService everyGroupService;
    private final SameSchoolValidator sameSchoolValidator;
    private final UserEveryGroupService userEveryGroupService;

    @Override
    public WriteCommentResponse writeComment(long userId, WriteCommentRequest dto,
        WriteCommentEntityDto from) {

        CommentEntity commentEntity = from.commentEntity();
        UserEntity findUserEntity = from.userEntity();

        EveryGroupEntity everyGroupEntity = everyGroupService.findEveryGroupEntityByGroupId(
            dto.groupId());

        sameSchoolValidator.isUserReadMySchoolEveryGroup(findUserEntity,
            everyGroupEntity);
        isUserAccessEveryGroupComment(findUserEntity, dto.isPublic(), dto.groupId());
        everyGroupService.writeCommentInGroup(commentEntity, dto.groupId());
        return WriteCommentResponse.of(commentEntity.getId());
    }

    @Override
    public ReadCommentResponse readComment(UserEntity findUserEntity, boolean isPublic,
        ReadCommentRequest dto) {
        EveryGroupEntity everyGroupEntity = everyGroupService.findEveryGroupEntityByGroupId(
            dto.groupId());
        sameSchoolValidator.isUserReadMySchoolEveryGroup(findUserEntity, everyGroupEntity);
        isUserAccessEveryGroupComment(findUserEntity, isPublic, dto.groupId());
        return ReadCommentResponse.of(everyGroupService.readCommentInGroup(findUserEntity, isPublic, dto.groupId()));
    }

    @Override
    public boolean supports(GroupType groupType) {
        return groupType.equals(GroupType.WEEKLY);
    }

    private void isUserAccessEveryGroupComment(UserEntity userEntity, boolean isPublic,
        long groupId) {
        if (!isPublic)
            userEveryGroupService.isValidCommentAccess(userEntity, groupId);
    }
}
