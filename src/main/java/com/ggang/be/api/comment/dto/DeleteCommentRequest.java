package com.ggang.be.api.comment.dto;

import com.ggang.be.domain.constant.GroupType;

public record DeleteCommentRequest(
        long groupId,
        long commentId,
        GroupType groupType,
        boolean isPublic) {
}
