package com.ggang.be.api.facade;

import com.ggang.be.api.user.service.UserService;
import com.ggang.be.domain.user.UserEntity;
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

    @Transactional
    public TokenVo login(Long userId) {
        UserEntity user = userService.getUserById(userId);

        String accessTokenJwt = jwtService.createAccessToken(userId);
        String refreshTokenJwt = jwtService.createRefreshToken(userId);

        user.updateRefreshToken(refreshTokenJwt);

        log.info("기존 유저 로그인 성공 - userId: {}", user.getId());
        return new TokenVo(user.getId(), accessTokenJwt, refreshTokenJwt);
    }

    public TokenVo login(String platformId) {
        String accessTokenJwt = getTempAccessToken(platformId);
        String refreshTokenJwt = getTempRefreshToken(platformId);

        return new TokenVo(null, accessTokenJwt, refreshTokenJwt);
    }

    private String getTempAccessToken(String platformId) {
        return jwtService.createTempAccessToken(platformId);
    }

    private String getTempRefreshToken(String platformId) {
        return jwtService.createTempRefreshToken(platformId);
    }
}
