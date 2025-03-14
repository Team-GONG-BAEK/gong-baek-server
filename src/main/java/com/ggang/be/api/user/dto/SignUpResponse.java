package com.ggang.be.api.user.dto;

public record SignUpResponse(Long userId, String accessToken, String refreshToken) {
    public static SignUpResponse of(Long userId, String accessToken, String refreshToken) {
        return new SignUpResponse(userId, accessToken, refreshToken);
    }
}
