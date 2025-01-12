package com.ggang.be.domain.group.vo;

import java.util.List;

public record ReadCommentGroup(int commentCount, List<GroupCommentVo> comments) {

    public static ReadCommentGroup of(int commentCount, List<GroupCommentVo> comments) {
        return new ReadCommentGroup(commentCount, comments);
    }
}
