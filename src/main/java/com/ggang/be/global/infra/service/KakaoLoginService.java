package com.ggang.be.global.infra.service;

import com.ggang.be.global.infra.oauth.KakaoOAuthClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class KakaoLoginService {

    private final KakaoOAuthClient kakaoOAuthClient;

    @Transactional
    public String login(String code) {
        String kakaoAccessToken = getKakaoAccessToken(code);
        return getPlatformId(kakaoAccessToken);
    }

    public String getKakaoAccessToken(String authCode) {
        return kakaoOAuthClient.getAccessToken(authCode);
    }

    public String getPlatformId(String accessToken) {
        log.info("getPlatformId: {}", accessToken);
        return kakaoOAuthClient.getUserInfo(accessToken).kakaoId();
    }
}
