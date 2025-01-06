package com.ggang.be.api.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseBuilder {

    public static <T> ResponseEntity<ApiResponse<T>> ok(T data){
        return ResponseEntity.ok(ApiResponse.success(ResponseSuccess.OK, data));
    }
    public static ResponseEntity<ApiResponse<Void>> badRequest() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error(ResponseError.BAD_REQUEST));
    }
}
