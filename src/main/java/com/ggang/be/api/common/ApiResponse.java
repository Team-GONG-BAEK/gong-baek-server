package com.ggang.be.api.common;

import lombok.Getter;

public record ApiResponse<T>(boolean success, int code, String message, T data) {
    public static <T> ApiResponse<T> success(ResponseSuccess responseSuccess, T data) {
        return new ApiResponse<>(true, responseSuccess.getCode(), responseSuccess.getMessage(), data);
    }

    public static <T> ApiResponse<T> success(ResponseSuccess responseSuccess) {
        return new ApiResponse<>(true, responseSuccess.getCode(), responseSuccess.getMessage(), null);
    }

    public static <T> ApiResponse<T> error(ResponseError responseError) {
        return new ApiResponse<>(false, responseError.getCode(), responseError.getMessage(), null);
    }

}