package com.ggang.be.api.exception;

import com.ggang.be.api.common.ApiResponse;
import com.ggang.be.api.common.ResponseBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
//    @ExceptionHandler(IllegalArgumentException.class)
//    ResponseEntity<ApiResponse> badRequest(IllegalArgumentException exception) {
//        return ResponseBuilder.badRequest();
//    }


}
