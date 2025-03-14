package com.ggang.be.api.facade;

import com.ggang.be.api.auth.service.PlatformAuthService;
import com.ggang.be.api.user.service.UserService;
import com.ggang.be.domain.constant.Platform;
import com.ggang.be.global.jwt.JwtService;
import com.ggang.be.global.jwt.TokenVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class LoginFacade {

    private final UserService userService;
    private final JwtService jwtService;
    private final PlatformAuthService platformAuthService;

    @Transactional
    public TokenVo login(String platformId, Platform platform) {
        return userService.getUserIdByPlatformAndPlatformId(platform, platformId)
                .map(this::login)
                .orElseGet(() -> createTemporaryToken(platformId));
    }

    @Transactional
    public void logout(Long userId) {
        userService.removeRefreshToken(userId);
    }

    public String getPlatformId(Platform platform, String authorization) {
        return platformAuthService.getPlatformId(platform, authorization);
    }

    private TokenVo login(Long userId) {
        log.info("기존 유저 로그인 성공 - userId: {}", userId);
        String newRefreshToken = jwtService.createRefreshToken(userId);

        userService.updateRefreshToken(newRefreshToken, userService.getUserById(userId));
        return new TokenVo(userId, jwtService.createAccessToken(userId), newRefreshToken);
    }

    private TokenVo createTemporaryToken(String platformId) {
        return new TokenVo(null, jwtService.createTempAccessToken(platformId), jwtService.createTempRefreshToken(platformId));
    }
}
