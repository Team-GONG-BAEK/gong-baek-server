package com.ggang.be.infra.service;

import com.ggang.be.infra.oauth.AppleOAuthClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppleLoginService {

    private final AppleOAuthClient appleOAuthClient;

    @Transactional
    public String getApplePlatformId(String code) {
        String appleIdToken = appleOAuthClient.getAccessToken(code);
        log.info("appleIdToken: {}", appleIdToken);

        return appleOAuthClient.getPlatformId(appleIdToken);
    }
}
