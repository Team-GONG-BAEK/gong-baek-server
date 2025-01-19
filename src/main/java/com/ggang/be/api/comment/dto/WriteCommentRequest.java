package com.ggang.be.api.comment.dto;

import com.ggang.be.domain.constant.GroupType;
import jakarta.validation.constraints.NotBlank;

public record WriteCommentRequest(Long groupId, GroupType groupType, boolean isPublic, @NotBlank String body) {
}
