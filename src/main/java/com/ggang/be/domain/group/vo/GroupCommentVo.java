package com.ggang.be.domain.group.vo;

import com.ggang.be.domain.comment.CommentEntity;
import com.ggang.be.domain.constant.GroupType;
import com.ggang.be.domain.group.everyGroup.EveryGroupEntity;
import com.ggang.be.domain.group.onceGroup.OnceGroupEntity;
import com.ggang.be.domain.user.UserEntity;

import java.time.format.DateTimeFormatter;

public record GroupCommentVo(
        long commentId,boolean isWriter,
        boolean isGroupHost, String nickname, String body, String createdAt
) {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");

    public static GroupCommentVo ofEveryGroup(
        UserEntity nowUserEntity,
        EveryGroupEntity groupEntity, CommentEntity commentEntity) {
        UserEntity commentUserEntity = commentEntity.getUserEntity();
        Long creatorId = groupEntity.getUserEntity().getId();

        return new GroupCommentVo(commentEntity.getId(),
            commentUserEntity.getId().equals(nowUserEntity.getId()),
            commentUserEntity.getId().equals(creatorId),
            commentUserEntity.getNickname(), commentEntity.getBody(),
            formatter.format(commentEntity.getCreatedAt()));
    }

    public static GroupCommentVo ofOnceGroup(
        UserEntity nowUserEntity,
        OnceGroupEntity groupEntity, CommentEntity commentEntity) {
        UserEntity commentUserEntity = commentEntity.getUserEntity();
        Long creatorId = groupEntity.getUserEntity().getId();

        return new GroupCommentVo(commentEntity.getId(),
            commentUserEntity.getId().equals(nowUserEntity.getId()),
            commentUserEntity.getId().equals(creatorId),
            commentUserEntity.getNickname(), commentEntity.getBody(),
            formatter.format(commentEntity.getCreatedAt()));
    }
}
