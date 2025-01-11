package com.ggang.be.api.comment.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.ggang.be.domain.vo.ReadCommentGroup;

public record ReadCommentResponse(@JsonUnwrapped ReadCommentGroup readCommentGroup) {

    public static ReadCommentResponse of(ReadCommentGroup readCommentGroup) {
        return new ReadCommentResponse(readCommentGroup);
    }

}
