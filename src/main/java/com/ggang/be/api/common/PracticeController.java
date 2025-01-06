package com.ggang.be.api.common;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PracticeController {

    @GetMapping("/success")
    public ResponseEntity<ApiResponse<String>> success() {
        return ResponseBuilder.ok("1");
    }

    @GetMapping("/custom_error")
    public ResponseEntity<ApiResponse<Void>> error() {
        return ResponseBuilder.badRequest();
    }

}
