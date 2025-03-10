package com.ggang.be.api.user.dto;

import com.ggang.be.domain.constant.Platform;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginRequest {
    private Platform platform;
    private String code;

    @Builder
    public LoginRequest(Platform platform, String authorizationCode) {
        this.platform = platform;
        this.code = authorizationCode;
    }
}
