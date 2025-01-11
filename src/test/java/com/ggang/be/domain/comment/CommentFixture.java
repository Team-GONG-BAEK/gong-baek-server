package com.ggang.be.domain.comment;

public class CommentFixture {


    public static CommentEntity getTestComment() {
        return CommentEntity.builder().
            body("test").
            isPublic(true)
            .userEntity(null)
            .build();
    }
}
