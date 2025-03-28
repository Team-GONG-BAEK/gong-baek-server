package com.ggang.be.domain.group.vo;

import com.ggang.be.domain.comment.CommentEntity;
import com.ggang.be.domain.group.everyGroup.EveryGroupEntity;
import com.ggang.be.domain.group.onceGroup.OnceGroupEntity;
import com.ggang.be.domain.user.UserEntity;

import java.time.format.DateTimeFormatter;

public record GroupCommentVo(
        long commentId, boolean isWriter,
        boolean isGroupHost, String nickname, String body, String createdAt
) {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm");

    public static GroupCommentVo ofEveryGroup(
            UserEntity nowUserEntity,
            EveryGroupEntity groupEntity,
            CommentEntity commentEntity
    ) {
        return from(nowUserEntity, groupEntity.getUserEntity(), commentEntity);
    }

    public static GroupCommentVo ofOnceGroup(
            UserEntity nowUserEntity,
            OnceGroupEntity groupEntity,
            CommentEntity commentEntity
    ) {
        return from(nowUserEntity, groupEntity.getUserEntity(), commentEntity);
    }

    private static GroupCommentVo from(
            UserEntity nowUserEntity,
            UserEntity groupCreator,
            CommentEntity commentEntity
    ) {
        UserEntity commentUser = commentEntity.getUserEntity();

        Long creatorId = groupCreator != null ? groupCreator.getId() : null;
        boolean isMine = false;
        boolean isHost = false;
        String nickname = null;

        if (commentUser != null) {
            isMine = commentUser.getId().equals(nowUserEntity.getId());
            isHost = creatorId != null && commentUser.getId().equals(creatorId);
            nickname = commentUser.getNickname();
        }

        return new GroupCommentVo(
                commentEntity.getId(),
                isMine,
                isHost,
                nickname,
                commentEntity.getBody(),
                formatter.format(commentEntity.getCreatedAt())
        );
    }
}
