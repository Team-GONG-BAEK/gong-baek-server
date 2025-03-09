package com.ggang.be.api.user.dto;

import com.ggang.be.domain.constant.Platform;
import lombok.Getter;

@Getter
public class LoginRequest {
    private Platform platform;
    private String code;
}
