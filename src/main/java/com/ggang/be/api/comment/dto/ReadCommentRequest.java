package com.ggang.be.api.comment.dto;

import com.ggang.be.domain.constant.GroupType;

public record ReadCommentRequest(long groupId, GroupType groupType) {

}
