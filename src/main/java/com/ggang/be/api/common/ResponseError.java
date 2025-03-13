package com.ggang.be.api.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ResponseError {

    // 400 Bad Request
    BAD_REQUEST(4000, "유효하지 않은 요청입니다."),
    INVALID_INPUT_VALUE(4001, "검증에 실패하였습니다."),
    INVALID_INPUT_IMAGE_EXTENSION(4002, "지원하지 않는 이미지 확장자입니다."),
    INVALID_INPUT_IMAGE_SIZE(4003, "지원하지 않는 이미지 크기입니다."),
    INVALID_INPUT_IMAGE_URL(4004, "잘못된 이미지 URL 입니다."),
    INVALID_INPUT_LENGTH(4005, "입력된 글자수가 허용된 범위를 벗어났습니다."),
    INVALID_INPUT_NICKNAME(4006, "닉네임은 한글로만 입력 가능합니다."),
    GROUP_ACCESS_SCHOOL_MISMATCH(4007, "같은 학교의 모임만 조회 가능합니다."),
    INVALID_REQUEST_PARAMETER(4008, "요청 파라미터가 잘못되었습니다."),
    INVALID_APPLE_AUTH_CODE(4009, "유효하지 않은 애플 로그인 코드입니다."),
    INVALID_KAKAO_AUTH_CODE(40010, "유효하지 않은 카카오 로그인 코드입니다."),


    // 401 Unauthorized
    UNAUTHORIZED_ACCESS(4010, "리소스 접근 권한이 없습니다."),
    INVALID_TOKEN(4011, "액세스 토큰의 값이 올바르지 않습니다."),
    EXPIRED_TOKEN(4012, "액세스 토큰이 만료되었습니다. 재발급 받아주세요."),
    INVALID_APPLE_ID_TOKEN(4013, "잘못된 애플 idToken 형식입니다."),
    APPLE_ID_TOKEN_DECODING_FAILED(4014, "애플 idToken 디코딩에 실패했습니다."),
    APPLE_ID_TOKEN_MISSING_SUB(4015, "유효하지 않은 애플 idToken 입니다."),
    INVALID_KAKAO_ACCESS_TOKEN(4016, "유효하지 않은 카카오 액세스 토큰입니다."),
    KAKAO_API_REQUEST_FAILED(4017, "카카오 API 요청에 실패했습니다."),
    INVALID_EMAIL_DOMAIN(4018, "이메일의 도메인 값이 올바르지 않습니다."),

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
    GROUP_ALREADY_EXIST(4093, "이미 해당 시간대 모임이 존재합니다."),
    GONGBAEK_TIME_SLOT_ALREADY_EXIST(4094, "이미 해당 공백 시간대의 시간이 존재합니다." ),
    TIME_SLOT_ALREADY_EXIST(4095, "이미 해당 시간표에는 강의 시간표가 존재합니다." ),
    APPLY_ALREADY_EXIST(4096, "이미 신청한 유저입니다."),
    GROUP_ALREADY_FULL(4097, "이미 인원이 마감된 모임입니다."),

    // 500 Internal Server Error
    INTERNAL_SERVER_ERROR(5000, "서버 내부 오류입니다."),
    APPLE_SECRET_GENERATION_FAILED(5001, "Apple Client Secret 생성에 실패했습니다."),
    UNABLE_TO_SEND_EMAIL(5002, "이메일을 전송할 수 없습니다.");

    private final int code;
    private final String message;

    ResponseError(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return HttpStatus.valueOf(code / 10);
    }
}
