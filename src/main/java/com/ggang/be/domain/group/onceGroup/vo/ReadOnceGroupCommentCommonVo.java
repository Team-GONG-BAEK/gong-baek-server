package com.ggang.be.domain.group.onceGroup.vo;

import com.ggang.be.domain.constant.GroupType;
import com.ggang.be.domain.constant.Status;

public record ReadOnceGroupCommentCommonVo(int commentCount, long groupId, GroupType groupType,
                                           Status status) {

    public static ReadOnceGroupCommentCommonVo of(int commentCount, long groupId, Status status) {
        return new ReadOnceGroupCommentCommonVo(commentCount, groupId, GroupType.ONCE, status);
    }

}
