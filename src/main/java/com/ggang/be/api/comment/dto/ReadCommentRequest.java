package com.ggang.be.api.comment.dto;

import com.ggang.be.api.facade.GroupType;

public record ReadCommentRequest(long groupId, GroupType groupType) {

}
