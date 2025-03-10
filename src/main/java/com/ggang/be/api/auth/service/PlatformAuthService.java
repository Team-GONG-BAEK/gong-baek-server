package com.ggang.be.api.auth.service;

import com.ggang.be.domain.constant.Platform;
import com.ggang.be.infra.service.AppleLoginService;
import com.ggang.be.infra.service.KakaoLoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlatformAuthService {
    private final KakaoLoginService kakaoLoginService;
    private final AppleLoginService appleLoginService;

    public String getPlatformId(Platform platform, String authorization) {
        return switch (platform) {
            case KAKAO -> kakaoLoginService.getKakaoPlatformId(authorization);
            case APPLE -> appleLoginService.getApplePlatformId(authorization);
        };
    }
}
