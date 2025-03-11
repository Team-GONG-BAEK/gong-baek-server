package com.ggang.be.api.user.dto;

import com.ggang.be.domain.constant.Platform;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginRequest {
    private Platform platform;

    @Builder
    public LoginRequest(Platform platform) {
        this.platform = platform;
    }
}
