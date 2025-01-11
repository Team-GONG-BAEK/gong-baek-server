package com.ggang.be.domain.group.vo;

import com.ggang.be.api.facade.GroupType;
import com.ggang.be.domain.comment.CommentEntity;
import com.ggang.be.domain.group.everyGroup.EveryGroupEntity;
import com.ggang.be.domain.group.onceGroup.OnceGroupEntity;
import com.ggang.be.domain.user.UserEntity;
import java.time.format.DateTimeFormatter;

public record GroupCommentVo(long groupId, GroupType groupType, long commentId, boolean isHost,
                             String nickname, String body, String createdAt) {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");


    public static GroupCommentVo ofEveryGroup(
        EveryGroupEntity groupEntity, CommentEntity commentEntity) {
        UserEntity commentUserEntity = commentEntity.getUserEntity();
        Long creatorId = groupEntity.getUserEntity().getId();

        return new GroupCommentVo(groupEntity.getId(), GroupType.WEEKLY, commentEntity.getId(),
            commentUserEntity.getId().equals(creatorId),
            commentUserEntity.getNickname(), commentEntity.getBody(),
            formatter.format(commentEntity.getCreatedAt()));
    }

    public static GroupCommentVo ofOnceGroup(
        OnceGroupEntity groupEntity, CommentEntity commentEntity) {
        UserEntity commentUserEntity = commentEntity.getUserEntity();
        Long creatorId = groupEntity.getUserEntity().getId();

        return new GroupCommentVo(groupEntity.getId(), GroupType.ONCE, commentEntity.getId(),
            commentUserEntity.getId().equals(creatorId),
            commentUserEntity.getNickname(), commentEntity.getBody(),
            formatter.format(commentEntity.getCreatedAt()));
    }
}
