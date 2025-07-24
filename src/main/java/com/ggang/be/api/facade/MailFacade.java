package com.ggang.be.api.facade;

import com.ggang.be.api.common.ResponseError;
import com.ggang.be.api.email.service.AppProperties;
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
    private final AppProperties appProperties;

    private static final String EMAIL_TITLE = "공백 학교 이메일 인증 안내";
    private static final String EMAIL_VERIFIED_CACHE_KEY = "email_verified:";

    public void sendCodeToEmail(String email, String schoolName) {
        // 이메일 중복 검사 (첫 번째 검증 시에만)
        userService.checkDuplicatedEmail(email);
        log.info("email: {}, appIosEmail: {}, appAndEmail: {} ", email, appProperties.getIosReviewEmail(), appProperties.getAndReviewEmail());
        
        verifyDomain(email, schoolName);

        String authCode = (email.equals(appProperties.getIosReviewEmail()) || email.equals(appProperties.getAndReviewEmail()))
                ? appProperties.getEmailCode()
                : mailService.createCode();

        // 이메일 중복 검사 통과 정보를 캐시에 저장 (인증 코드와 함께)
        authCodeCacheService.saveAuthCode(email, authCode);
        authCodeCacheService.saveEmailVerifiedStatus(email, true);

        mailService.sendEmail(email, EMAIL_TITLE, authCode);
    }

    public void verifiedCode(String email, String schoolName, String authCode) {
        // 캐시에서 이메일 검증 상태 확인
        Boolean isEmailVerified = authCodeCacheService.isEmailVerified(email);
        
        // 캐시에 검증 정보가 없는 경우에만 중복 검사 실행
        if (isEmailVerified == null || !isEmailVerified) {
            userService.checkDuplicatedEmail(email);
        }
        
        if (!email.equals(appProperties.getIosReviewEmail()) && !email.equals(appProperties.getAndReviewEmail())) {
            verifyDomain(email, schoolName);
        }

        String cachedAuthCode = authCodeCacheService.getAuthCode(email);

        if (cachedAuthCode == null || !cachedAuthCode.equals(authCode)) {
            throw new GongBaekException(ResponseError.INVALID_INPUT_VALUE);
        }

        authCodeCacheService.removeCode(email);
        authCodeCacheService.removeEmailVerifiedStatus(email);
    }

    private void verifyDomain(String email, String schoolName) {
        if (!email.equals(appProperties.getIosReviewEmail()) && !email.equals(appProperties.getAndReviewEmail())) {
            String schoolDomain = schoolService.findSchoolDomainByName(schoolName);
            String[] emailDomain = email.substring(email.indexOf("@") + 1).split("\\.");

            if (!Arrays.asList(emailDomain).contains(schoolDomain)) {
                log.info("schoolDomain: {}, emailDomainParts: {}", schoolDomain, Arrays.toString(emailDomain));
                throw new GongBaekException(ResponseError.INVALID_EMAIL_DOMAIN);
            }
        }
    }
}
