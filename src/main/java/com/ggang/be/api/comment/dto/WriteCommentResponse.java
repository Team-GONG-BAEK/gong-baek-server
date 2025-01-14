package com.ggang.be.api.comment.dto;

public record WriteCommentResponse(long commentId) {

    public static WriteCommentResponse of(long commentId) {
        return new WriteCommentResponse(commentId);
    }
}
