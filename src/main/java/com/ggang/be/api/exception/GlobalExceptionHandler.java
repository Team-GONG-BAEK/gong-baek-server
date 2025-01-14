package com.ggang.be.api.exception;

import com.ggang.be.api.common.ApiResponse;
import com.ggang.be.api.common.ResponseBuilder;
import com.ggang.be.api.common.ResponseError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(GongBaekException.class)
    public ResponseEntity<ApiResponse<Void>> handleGlobalException(GongBaekException e) {
        log.error("GongBaekException occurred", e);
        return ResponseBuilder.error(e.getResponseError());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationException(MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException occurred", e);
        return ResponseEntity
                .status(ResponseError.BAD_REQUEST.getHttpStatus())
                .body(ApiResponse.error(ResponseError.INVALID_INPUT_VALUE));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Void>> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("HttpMessageNotReadableException occurred", e);
        return ResponseBuilder.error(ResponseError.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGenericException(Exception e) {
        log.error("Exception occurred", e);
        return ResponseBuilder.error(ResponseError.INTERNAL_SERVER_ERROR);
    }
}
