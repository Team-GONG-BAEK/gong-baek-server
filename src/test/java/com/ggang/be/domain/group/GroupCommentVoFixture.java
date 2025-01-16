package com.ggang.be.domain.group;

import com.ggang.be.domain.constant.GroupType;
import com.ggang.be.domain.group.vo.GroupCommentVo;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GroupCommentVoFixture {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");

    public static GroupCommentVo createGroupCommentOnceVo(long commentId,LocalDateTime now) {
        return new GroupCommentVo(
            commentId,
            true,
            true,
            "TestUser",
            "This is a sample comment body.",
            now.format(formatter)
        );
    }


    public static GroupCommentVo createGroupCommentEveryVo(long commentId, LocalDateTime now) {
        return new GroupCommentVo(
            commentId,
            true,
            true,
            "TestUser",
            "This is a sample comment body.",
            now.format(formatter)
        );
    }

}
