package com.ggang.be.api.common;

import lombok.Getter;

@Getter
public enum ResponseError {
    BAD_REQUEST(4000, "유효하지 않은 요청입니다."),
    NOT_FOUND(4040, "대상을 찾을 수 없습니다."),
    INTERNEL_SERVER_ERROR(5000, "서버 내부 오류입니다.");

    private final int code;
    private final String message;

    ResponseError(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
