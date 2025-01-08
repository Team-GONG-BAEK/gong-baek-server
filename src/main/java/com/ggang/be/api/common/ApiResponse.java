package com.ggang.be.api.common;

import lombok.Getter;

@Getter
public class  ApiResponse<T> {
    private final boolean success;
    private final int code;
    private final String message;
    private final T data;

    public static <T> ApiResponse<T> success(ResponseSuccess responseSuccess, T data) {
        return new ApiResponse<>(true, responseSuccess.getCode(), responseSuccess.getMessage(), data);
    }

    public static <T> ApiResponse<T> success(ResponseSuccess responseSuccess) {
        return new ApiResponse<>(true, responseSuccess.getCode(), responseSuccess.getMessage(), null);
    }

    public static <T> ApiResponse <T> error(ResponseError responseError) {
        return new ApiResponse<>(false, responseError.getCode(), responseError.getMessage(), null);
    }

    public ApiResponse(boolean success, int code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }
}