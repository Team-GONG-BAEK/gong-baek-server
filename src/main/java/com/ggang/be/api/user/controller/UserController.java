package com.ggang.be.api.user.controller;

import com.ggang.be.api.common.ApiResponse;
import com.ggang.be.api.common.ResponseBuilder;
import com.ggang.be.api.common.ResponseError;
import com.ggang.be.api.exception.GongBaekException;
import com.ggang.be.api.facade.LoginFacade;
import com.ggang.be.api.facade.SignUpFacade;
import com.ggang.be.api.facade.SignupRequestFacade;
import com.ggang.be.api.user.NicknameValidator;
import com.ggang.be.api.user.dto.*;
import com.ggang.be.api.user.service.UserService;
import com.ggang.be.domain.constant.Platform;
import com.ggang.be.domain.user.dto.UserSchoolDto;
import com.ggang.be.global.jwt.JwtService;
import com.ggang.be.global.jwt.TokenVo;
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
    private final SignUpFacade signupFacade;
    private final LoginFacade loginFacade;
    private final SignupRequestFacade signupRequestFacade;
    private final JwtService jwtService;

    private final static int INTRODUCTION_MIN_LENGTH = 20;
    private final static int INTRODUCTION_MAX_LENGTH = 100;

    @PostMapping("/user/validate/introduction")
    public ResponseEntity<ApiResponse<Void>> validateIntroduction(@RequestBody final ValidIntroductionRequest dto) {
        if(LengthValidator.rangelengthCheck(dto.introduction(), INTRODUCTION_MIN_LENGTH, INTRODUCTION_MAX_LENGTH))
            return ResponseBuilder.ok(null);
        throw new GongBaekException(ResponseError.INVALID_INPUT_LENGTH);
    }

    @PostMapping("/user/validate/nickname")
    public ResponseEntity<ApiResponse<Void>> validateNickname(@RequestParam final String nickname) {
            NicknameValidator.validate(nickname);
            signupFacade.duplicateCheckNickname(nickname);
            return ResponseBuilder.ok(null);
    }

    @PostMapping("/user/signup")
    public ResponseEntity<ApiResponse<SignUpResponse>> signup(
            @RequestHeader("Authorization") String accessToken,
            @RequestBody final SignUpRequest request
    ) {
        log.info("Received Authorization Header: {}", accessToken);

        String platformId = jwtService.extractPlatformUserIdFromToken(accessToken);
        log.info("SignUp - platformId: {}", platformId);

        signupRequestFacade.validateSignupRequest(request);

        return ResponseBuilder.created(
                signupFacade.signUp(platformId, request)
        );
    }
  
    @GetMapping("/user/home/profile")
    public ResponseEntity<ApiResponse<UserSchoolResponseDto>> getGroupInfo(
            @RequestHeader("Authorization") final String accessToken
    ) {
        Long userId = jwtService.parseTokenAndGetUserId(accessToken);
        UserSchoolDto userSchoolDto = userService.getUserSchoolById(userId);

        return ResponseBuilder.ok(UserSchoolResponseDto.of(userSchoolDto));
    }

    @PatchMapping("/reissue/token")
    public ResponseEntity<ApiResponse<TokenVo>> reIssueToken(
        @RequestHeader("Authorization") String refreshToken) {
        return ResponseBuilder.ok(jwtService.reIssueToken(refreshToken));
    }

    @PostMapping("/kakao/login")
    public ResponseEntity<ApiResponse<TokenVo>> socialLogin(@RequestBody final LoginRequest request) {
        String platformId = loginFacade.getPlatformId(request);
        return userService.getUserIdByPlatformAndPlatformId(request.getPlatform(), platformId)
                .map(userId -> loginFacade.login(platformId, request.getPlatform()))
                .map(ResponseBuilder::ok)
                .orElseGet(() -> ResponseBuilder.created(loginFacade.login(platformId, request.getPlatform())));
    }

    @PostMapping("/apple/login")
    public ResponseEntity<ApiResponse<TokenVo>> socialLogin(
            @RequestParam("code") String authorizationCode,
            @RequestParam(value = "id_token", required = false) String idToken
    ) {
        log.info("Received authorizationCode: {}", authorizationCode);

        String platformId = loginFacade.getPlatformId(new LoginRequest(Platform.APPLE, authorizationCode));

        return userService.getUserIdByPlatformAndPlatformId(Platform.APPLE, platformId)
                .map(userId -> loginFacade.login(platformId, Platform.APPLE))
                .map(ResponseBuilder::ok)
                .orElseGet(() -> ResponseBuilder.created(loginFacade.login(platformId, Platform.APPLE)));
    }

}
