package com.ggang.be.api.exception;

import com.ggang.be.api.common.ApiResponse;
import com.ggang.be.api.common.ResponseBuilder;
import com.ggang.be.api.common.ResponseError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GongBaekException.class)
    public ResponseEntity<?> handleGlobalException(GongBaekException e) {
        return ResponseBuilder.error(e.getResponseError());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationException(MethodArgumentNotValidException e) {
        return ResponseEntity
                .status(ResponseError.BAD_REQUEST.getHttpStatus())
                .body(ApiResponse.error(ResponseError.INVALID_INPUT_VALUE));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGenericException(Exception e) {
        return ResponseBuilder.error(ResponseError.INTERNAL_SERVER_ERROR);
    }
}
