package com.ggang.be.api.user.controller;

import com.ggang.be.api.common.ApiResponse;
import com.ggang.be.api.common.ResponseBuilder;
import com.ggang.be.api.common.ResponseError;
import com.ggang.be.api.common.ResponseSuccess;
import com.ggang.be.api.user.dto.UserSchoolResponseDto;
import com.ggang.be.api.user.dto.ValidIntroductionRequestDto;
import com.ggang.be.api.user.service.UserService;
import com.ggang.be.global.util.LengthValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    private final static int INTRODUCTION_MIN_LENGTH = 20;
    private final static int INTRODUCTION_MAX_LENGTH = 100;


    @GetMapping("/user/introduction")
    public ResponseEntity<ApiResponse<Void>> validateIntroduction(@RequestBody final ValidIntroductionRequestDto dto) {
        if(LengthValidator.rangelengthCheck(dto.introduction(), INTRODUCTION_MIN_LENGTH, INTRODUCTION_MAX_LENGTH))
            return ResponseBuilder.ok(null);
        return ResponseBuilder.error(ResponseError.INVALID_INPUT_LENGTH);
    }

    @GetMapping("/user/school")
    public ResponseEntity<ApiResponse<UserSchoolResponseDto>> getGroupInfo(
            @RequestHeader("Authorization") String accessToken
    ) {
        UserSchoolResponseDto userSchoolResponseDto = userService.getUserSchoolById(Long.parseLong(accessToken));
        return ResponseEntity.ok(ApiResponse.success(ResponseSuccess.OK, userSchoolResponseDto));
    }

}
