package com.ggang.be.global.jwt;

public record TokenVo(Long userId, String accessToken, String refreshToken) {

    public static TokenVo of(Long userId, String accessToken, String refreshToken) {
        return new TokenVo(userId, accessToken, refreshToken);
    }
}
