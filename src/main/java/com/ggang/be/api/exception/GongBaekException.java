package com.ggang.be.api.exception;

import com.ggang.be.api.common.ResponseError;
import lombok.Getter;

@Getter
public class GongBaekException extends RuntimeException {
    private final ResponseError responseError;

    public GongBaekException(ResponseError responseError) {
        super(responseError.getMessage());
        this.responseError = responseError;
    }
}
