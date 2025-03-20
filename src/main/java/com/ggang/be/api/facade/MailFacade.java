package com.ggang.be.api.facade;

import com.ggang.be.api.common.ResponseError;
import com.ggang.be.api.email.service.AuthCodeCacheService;
import com.ggang.be.api.email.service.MailService;
import com.ggang.be.api.exception.GongBaekException;
import com.ggang.be.api.school.service.SchoolService;
import com.ggang.be.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
@Slf4j
public class MailFacade {
    private final UserService userService;
    private final MailService mailService;
    private final SchoolService schoolService;
    private final AuthCodeCacheService authCodeCacheService;

    private static final String EMAIL_TITLE = "공백 학교 이메일 인증 안내";

    public void sendCodeToEmail(String toEmail, String schoolName) {
        userService.checkDuplicatedEmail(toEmail);
        verifyDomain(toEmail, schoolName);

        String authCode = mailService.createCode();
        authCodeCacheService.saveAuthCode(toEmail, authCode);

        mailService.sendEmail(toEmail, EMAIL_TITLE, authCode);
    }

    public void verifiedCode(String email, String schoolName, String authCode) {
        userService.checkDuplicatedEmail(email);
        verifyDomain(email, schoolName);

        String cachedAuthCode = authCodeCacheService.getAuthCode(email);

        if (cachedAuthCode == null || !cachedAuthCode.equals(authCode)) {
            throw new GongBaekException(ResponseError.INVALID_INPUT_VALUE);
        }

        authCodeCacheService.removeCode(email);
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
