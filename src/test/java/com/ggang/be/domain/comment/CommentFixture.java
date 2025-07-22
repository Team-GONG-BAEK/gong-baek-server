package com.ggang.be.domain.comment;

import com.ggang.be.domain.user.fixture.UserEntityFixture;

public class CommentFixture {

    public static CommentEntity getTestComment() {
        return CommentEntity.builder().
                body("test").
                isPublic(true)
                .userEntity(UserEntityFixture.create())
                .build();
    }

    public static CommentEntity getTestCommentIsPublic(boolean isPublic) {
        return CommentEntity.builder().
                body("test").
                isPublic(isPublic)
                .userEntity(UserEntityFixture.create())
                .build();
    }

}
