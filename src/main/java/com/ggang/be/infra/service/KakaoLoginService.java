package com.ggang.be.infra.service;

import com.ggang.be.infra.oauth.KakaoOAuthClient;
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
    public String getKakaoPlatformId(String code) {
        String kakaoAccessToken = kakaoOAuthClient.getAccessToken(code);
        log.info("kakaoAccessToken: {}", kakaoAccessToken);

        return kakaoOAuthClient.getPlatformId(kakaoAccessToken);
    }
}
