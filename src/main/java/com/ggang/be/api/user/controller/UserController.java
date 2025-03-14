package com.ggang.be.api.user.controller;

import com.ggang.be.api.common.ApiResponse;
import com.ggang.be.api.common.ResponseBuilder;
import com.ggang.be.api.common.ResponseError;
import com.ggang.be.api.exception.GongBaekException;
import com.ggang.be.api.facade.LoginFacade;
import com.ggang.be.api.facade.MailFacade;
import com.ggang.be.api.facade.SignUpFacade;
import com.ggang.be.api.facade.SignupRequestFacade;
import com.ggang.be.api.user.NicknameValidator;
import com.ggang.be.api.user.dto.*;
import com.ggang.be.api.user.service.UserService;
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
    private final MailFacade mailFacade;

    private final static int INTRODUCTION_MIN_LENGTH = 0;
    private final static int INTRODUCTION_MAX_LENGTH = 100;

    @PostMapping("/user/validate/introduction")
    public ResponseEntity<ApiResponse<Void>> validateIntroduction(@RequestBody final ValidIntroductionRequest dto) {
        if(LengthValidator.rangeLengthCheck(dto.introduction(), INTRODUCTION_MIN_LENGTH, INTRODUCTION_MAX_LENGTH))
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
            @RequestHeader("Authorization") String accessToken
    ) {
        Long userId = jwtService.parseTokenAndGetUserId(accessToken);

        return ResponseBuilder.ok(
                UserSchoolResponseDto.of(userService.getUserSchoolById(userId))
        );
    }

    @GetMapping("/user/my/profile")
    public ResponseEntity<ApiResponse<UserProfileResponse>> getUserProfile(
            @RequestHeader("Authorization") String accessToken
    ) {
        Long userId = jwtService.parseTokenAndGetUserId(accessToken);
        return ResponseBuilder.ok(
                UserProfileResponse.of(userService.getUserInfoById(userId))
        );
    }

    @PatchMapping("/reissue/token")
    public ResponseEntity<ApiResponse<TokenVo>> reIssueToken(
        @RequestHeader("Authorization") String refreshToken) {
        return ResponseBuilder.ok(jwtService.reIssueToken(refreshToken));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<TokenVo>> login(
            @RequestHeader("Authorization") String authorization,
            @RequestBody final LoginRequest request
    ) {
        log.info("소셜 로그인 요청 - Platform: {}, Code: {}", request.getPlatform(), authorization);

        String platformId = loginFacade.getPlatformId(request.getPlatform(), authorization);

        return userService.getUserIdByPlatformAndPlatformId(request.getPlatform(), platformId)
                .map(userId -> loginFacade.login(platformId, request.getPlatform()))
                .map(ResponseBuilder::ok)
                .orElseGet(() -> ResponseBuilder.created(loginFacade.login(platformId, request.getPlatform())));
    }

    @PostMapping("/emails/verification-requests")
    public ResponseEntity<ApiResponse<Void>> sendMessage(
            @RequestParam("email") String email,
            @RequestParam("schoolName") String schoolName
            ) {
        mailFacade.sendCodeToEmail(email, schoolName);

        return ResponseBuilder.created(null);
    }

    @GetMapping("/emails/verifications")
    public ResponseEntity<ApiResponse<Void>> verificationEmail(
            @RequestParam("email") String email,
            @RequestParam("schoolName") String schoolName,
            @RequestParam("code") String authCode
    ) {
        mailFacade.verifiedCode(email, schoolName, authCode);

        return ResponseBuilder.ok(null);
    }
}
