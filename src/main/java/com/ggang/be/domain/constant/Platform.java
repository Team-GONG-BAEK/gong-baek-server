package com.ggang.be.domain.constant;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum Platform {
    APPLE("APPLE"),
    KAKAO("KAKAO");

    private final String stringPlatform;
}