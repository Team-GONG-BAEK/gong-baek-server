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
    public String getApplePlatformId(String identityToken) {
        log.info("Apple OAuth - Identity Token received: {}", identityToken);

        return appleOAuthClient.getPlatformId(identityToken);
    }
}
