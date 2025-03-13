package com.ggang.be.api.facade;

import com.ggang.be.api.common.ResponseError;
import com.ggang.be.api.email.service.EmailProperties;
import com.ggang.be.api.email.service.MailService;
import com.ggang.be.api.exception.GongBaekException;
import com.ggang.be.api.user.service.UserService;
import com.ggang.be.infra.redis.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
@Slf4j
public class MailFacade {
    private final EmailProperties emailProperties;
    private final UserService userService;
    private final MailService mailService;
    private final RedisService redisService;

    private static final String AUTH_CODE_PREFIX = "AuthCode ";
    private static final String EMAIL_TITLE = "공백 학교 이메일 인증 안내";

    public void sendCodeToEmail(String toEmail) {
        userService.checkDuplicatedEmail(toEmail);
        String authCode = mailService.createCode();
        mailService.sendEmail(toEmail, EMAIL_TITLE, authCode);

        redisService.saveValue(AUTH_CODE_PREFIX + toEmail,
                authCode, Duration.ofMillis(emailProperties.getAuthCodeExpirationMillis()));
    }

    public void verifiedCode(String email, String authCode) {
        userService.checkDuplicatedEmail(email);
        String redisKey = AUTH_CODE_PREFIX + email;
        String redisAuthCode = redisService.getValue(redisKey);

        if (redisAuthCode == null || !redisService.checkExistsValue(redisKey) || !redisAuthCode.equals(authCode)) {
            throw new GongBaekException(ResponseError.INVALID_INPUT_VALUE);
        }
    }
}
