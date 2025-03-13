package com.ggang.be.api.facade;

import com.ggang.be.api.common.ResponseError;
import com.ggang.be.api.email.service.EmailProperties;
import com.ggang.be.api.email.service.MailService;
import com.ggang.be.api.exception.GongBaekException;
import com.ggang.be.api.school.service.SchoolService;
import com.ggang.be.api.user.service.UserService;
import com.ggang.be.infra.redis.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
@Slf4j
public class MailFacade {
    private final EmailProperties emailProperties;
    private final UserService userService;
    private final MailService mailService;
    private final SchoolService schoolService;
    private final RedisService redisService;

    private static final String AUTH_CODE_PREFIX = "AuthCode ";
    private static final String EMAIL_TITLE = "공백 학교 이메일 인증 안내";

    public void sendCodeToEmail(String toEmail, String schoolName) {
        userService.checkDuplicatedEmail(toEmail);
        verifyDomain(toEmail, schoolName);
        String authCode = mailService.createCode();
        mailService.sendEmail(toEmail, EMAIL_TITLE, authCode);

        redisService.saveValue(AUTH_CODE_PREFIX + toEmail,
                authCode, Duration.ofMillis(emailProperties.getAuthCodeExpirationMillis()));
    }

    public void verifiedCode(String email, String schoolName, String authCode) {
        userService.checkDuplicatedEmail(email);
        String redisKey = AUTH_CODE_PREFIX + email;
        String redisAuthCode = redisService.getValue(redisKey);

        verifyDomain(email, schoolName);

        if (redisAuthCode == null || !redisService.checkExistsValue(redisKey) || !redisAuthCode.equals(authCode)) {
            throw new GongBaekException(ResponseError.INVALID_INPUT_VALUE);
        }
    }

    private void verifyDomain(String email, String schoolName) {
        String schoolDomain = schoolService.findSchoolDomainByName(schoolName);
        String[] emailDomain = email.substring(email.indexOf("@") + 1).split("\\.");

        if (!Arrays.asList(emailDomain).contains(schoolDomain)) {
            log.info("schoolDomain: {}, emailDomainParts: {}", schoolDomain, Arrays.toString(emailDomain));
            throw new GongBaekException(ResponseError.INVALID_EMAIL_DOMAIN);
        }
    }
}
