package com.ggang.be.api.comment.dto;

import com.ggang.be.api.facade.GroupType;

public record WriteCommentRequest(Long groupId, GroupType groupType, boolean isPublic, String body) {
}
