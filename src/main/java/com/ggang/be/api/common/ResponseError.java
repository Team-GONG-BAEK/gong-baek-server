package com.ggang.be.api.common;

import lombok.Getter;

@Getter
public enum ResponseError {

    // 400 Bad Request
    BAD_REQUEST(4000, "유효하지 않은 요청입니다."),
    INVALID_INPUT_VALUE(4001, "검증에 실패하였습니다."),
    INVALID_INPUT_IMAGE_VALUE(4002, "지원하지 않는 이미지 확장자입니다."),
    INVALID_INPUT_IMAGE_SIZE(4003, "지원하지 않는 이미지 크기입니다."),
    INVALID_INPUT_IMAGE_URL(4004, "잘못된 이미지 URL 입니다."),
    INVALID_INPUT_LENGTH(4005, "글자수를 초과했습니다."),

    // 401 Unauthorized
    UNAUTHORIZED_ACCESS(4010, "리소스 접근 권한이 없습니다."),
    INVALID_TOKEN(4011, "액세스 토큰의 값이 올바르지 않습니다."),
    EXPIRED_TOKEN(4012, "액세스 토큰이 만료되었습니다. 재발급 받아주세요."),

    // 404 Not Found
    NOT_FOUND(4040, "대상을 찾을 수 없습니다."),
    TOKEN_NOT_FOUND(4041, "찾을 수 없는 토큰 타입입니다."),
    USER_NOT_FOUND(4042, "유저를 찾을 수 없습니다."),
    COMMENT_NOT_FOUND(4043, "해당 댓글을 찾을 수 없습니다."),
    GROUP_NOT_FOUND(4044, "해당 모임을 찾을 수 없습니다."),
    IMAGE_NOT_FOUND(4045, "이미지를 찾을 수 없습니다."),
    COMING_GROUP_NOT_FOUND(4046, "다가오는 모임을 찾을 수 없습니다."),
    GROUP_DELETE_NOT_FOUND(4047, "해당 모임을 삭제할 수 없습니다."),
    GROUP_CANCEL_NOT_FOUND(4048, "해당 모임 신청을 취소할 수 없습니다."),
    ADVERTISEMENT_NOT_FOUND(4049, "해당 광고를 찾을 수 없습니다."),

    // 409 Conflict
    CONFLICT(4090, "이미 존재하는 리소스 입니다."),
    USERNAME_ALREADY_EXISTS(4091, "이미 존재하는 유저입니다."),
    NICKNAME_ALREADY_EXISTS(4092, "이미 존재하는 닉네임입니다."),

    // 500 Internal Server Error
    INTERNAL_SERVER_ERROR(5000, "서버 내부 오류입니다.");

    private final int code;
    private final String message;

    ResponseError(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
