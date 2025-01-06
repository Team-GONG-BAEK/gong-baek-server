package com.ggang.be.api.common;

import lombok.Getter;

@Getter
public enum ResponseSuccess {
    OK(2000, "성공하였습니다."),
    CREATED(2010, "리소스가 성공적으로 생성되었습니다.");

    private final int code;
    private final String message;

    ResponseSuccess(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
