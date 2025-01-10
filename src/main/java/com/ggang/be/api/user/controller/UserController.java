package com.ggang.be.api.user.controller;

import com.ggang.be.api.common.ApiResponse;
import com.ggang.be.api.common.ResponseBuilder;
import com.ggang.be.api.common.ResponseError;
import com.ggang.be.api.exception.GongBaekException;
import com.ggang.be.api.facade.SignupFacade;
import com.ggang.be.api.user.NicknameValidator;
import com.ggang.be.api.user.dto.ValidIntroductionRequestDto;
import com.ggang.be.global.util.LengthValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final static int INTRODUCTION_MIN_LENGTH = 20;
    private final static int INTRODUCTION_MAX_LENGTH = 100;
    private final SignupFacade signupFacade;


    @GetMapping("/user/validate/introduction")
    public ResponseEntity<ApiResponse<Void>> validateIntroduction(@RequestBody final ValidIntroductionRequestDto dto) {
        if(LengthValidator.rangelengthCheck(dto.introduction(), INTRODUCTION_MIN_LENGTH, INTRODUCTION_MAX_LENGTH))
            return ResponseBuilder.ok(null);
        return ResponseBuilder.error(ResponseError.INVALID_INPUT_LENGTH);
    }

    @PostMapping("/user/validate/nickname")
    public ResponseEntity<ApiResponse<Void>> validateNickname(@RequestParam final String nickname) {
            NicknameValidator.validate(nickname);
            signupFacade.duplicateCheckNickname(nickname);
            return ResponseBuilder.ok(null);
    }

}
