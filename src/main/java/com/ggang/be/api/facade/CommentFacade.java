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
import com.ggang.be.api.userEveryGroup.service.UserEveryGroupService;
import com.ggang.be.api.userOnceGroup.service.UserOnceGroupService;
import com.ggang.be.domain.comment.CommentEntity;
import com.ggang.be.domain.common.SameSchoolValidator;
import com.ggang.be.domain.constant.GroupType;
import com.ggang.be.domain.group.everyGroup.EveryGroupEntity;
import com.ggang.be.domain.group.onceGroup.OnceGroupEntity;
import com.ggang.be.domain.group.vo.ReadCommentGroup;
import com.ggang.be.domain.user.UserEntity;
import com.ggang.be.global.annotation.Facade;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Facade
@RequiredArgsConstructor
public class CommentFacade {

    private final EveryGroupService everyGroupService;
    private final OnceGroupService onceGroupService;
    private final CommentService commentService;
    private final UserEveryGroupService userEveryGroupService;
    private final UserOnceGroupService userOnceGroupService;
    private final UserService userService;
    private final SameSchoolValidator sameSchoolValidator;

    @Transactional
    public WriteCommentResponse writeComment(final long userId, WriteCommentRequest dto) {
        UserEntity findUserEntity = userService.getUserById(userId);
        CommentEntity commentEntity = commentService.writeComment(findUserEntity, dto);

        writeByCase(dto, findUserEntity, commentEntity);

        return WriteCommentResponse.of(commentEntity.getId());
    }

    private void writeByCase(WriteCommentRequest dto, UserEntity findUserEntity, CommentEntity commentEntity) {
        if (dto.groupType() == GroupType.WEEKLY) {
            EveryGroupEntity everyGroupEntity = everyGroupService.findEveryGroupEntityByGroupId(
                dto.groupId());
            sameSchoolValidator.isUserReadMySchoolEveryGroup(commentEntity.getUserEntity(),
                everyGroupEntity);
            isUserAccessEveryGroupComment(findUserEntity, dto.isPublic(), dto.groupId());
            everyGroupService.writeCommentInGroup(commentEntity, dto.groupId());
        }
        if (dto.groupType() == GroupType.ONCE) {
            OnceGroupEntity onceGroupEntity = onceGroupService.findOnceGroupEntityByGroupId(
                dto.groupId());
            sameSchoolValidator.isUserReadMySchoolOnceGroup(commentEntity.getUserEntity(),
                onceGroupEntity);
            isUserAccessOnceGroupComment(findUserEntity, dto.isPublic(), dto.groupId());
            onceGroupService.writeCommentInGroup(commentEntity, dto.groupId());
        }
    }

    public ReadCommentResponse readComment(Long userId, final boolean isPublic,
        ReadCommentRequest dto) {
        UserEntity findUserEntity = userService.getUserById(userId);

        return ReadCommentResponse.of(readByCase(findUserEntity, isPublic, dto));
    }

    private ReadCommentGroup readByCase(UserEntity userEntity, boolean isPublic,
        ReadCommentRequest dto) {
        if (dto.groupType() == GroupType.WEEKLY) {
            EveryGroupEntity everyGroupEntity = everyGroupService.findEveryGroupEntityByGroupId(
                dto.groupId());
            sameSchoolValidator.isUserReadMySchoolEveryGroup(userEntity, everyGroupEntity);
            isUserAccessEveryGroupComment(userEntity, isPublic, dto.groupId());
            return everyGroupService.readCommentInGroup(userEntity, isPublic, dto.groupId());
        }
        if (dto.groupType() == GroupType.ONCE) {
            OnceGroupEntity onceGroupEntity = onceGroupService.findOnceGroupEntityByGroupId(
                dto.groupId());
            sameSchoolValidator.isUserReadMySchoolOnceGroup(userEntity, onceGroupEntity);
            isUserAccessOnceGroupComment(userEntity, isPublic, dto.groupId());
            return onceGroupService.readCommentInGroup(userEntity, isPublic, dto.groupId());
        }
        throw new GongBaekException(ResponseError.BAD_REQUEST);
    }

    private void isUserAccessOnceGroupComment(UserEntity userEntity, boolean isPublic,
        long groupId) {
        if (!isPublic) {
            userOnceGroupService.isValidCommentAccess(userEntity, groupId);
        }
    }

    private void isUserAccessEveryGroupComment(UserEntity userEntity, boolean isPublic,
        long groupId) {
        if (!isPublic)
            userEveryGroupService.isValidCommentAccess(userEntity, groupId);
    }

}
