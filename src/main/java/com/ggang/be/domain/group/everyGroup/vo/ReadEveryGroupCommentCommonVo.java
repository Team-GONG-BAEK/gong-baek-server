package com.ggang.be.domain.group.everyGroup.vo;

import com.ggang.be.domain.constant.GroupType;
import com.ggang.be.domain.constant.Status;

public record ReadEveryGroupCommentCommonVo(int commentCount, long groupId, GroupType groupType,
                                            Status status) {

    public static ReadEveryGroupCommentCommonVo of(int commentCount, long groupId,
        Status status) {
        return new ReadEveryGroupCommentCommonVo(commentCount, groupId, GroupType.WEEKLY, status);
    }

}
