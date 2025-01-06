package com.ggang.be.api.common;

import lombok.Getter;

@Getter
public enum ResponseSuccess {

    //TODO : Notion에 예쁘게 코드 정리해놓기
    OK(2000, "성공하였습니다.");


    private final int code;
    private final String message;

    ResponseSuccess(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
