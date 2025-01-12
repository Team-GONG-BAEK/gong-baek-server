package com.ggang.be.domain.comment;

public class CommentFixture {


    public static CommentEntity getTestComment() {
        return CommentEntity.builder().
            body("test").
            isPublic(true)
            .userEntity(null)
            .build();
    }

    public static CommentEntity getTestCommentIsPublic(boolean isPublic) {
        return CommentEntity.builder().
            body("test").
            isPublic(isPublic)
            .userEntity(null)
            .build();
    }

}
