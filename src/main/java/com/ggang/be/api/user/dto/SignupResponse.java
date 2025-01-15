package com.ggang.be.api.user.dto;

public record SignupResponse(Long userId, String accessToken, String refreshToken) {
    public static SignupResponse of(Long userId, String accessToken, String refreshToken) {
        return new SignupResponse(userId, accessToken, refreshToken);
    }
}
