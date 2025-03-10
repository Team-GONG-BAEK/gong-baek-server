package com.ggang.be.global.infra.oauth;

import com.ggang.be.global.infra.Auth;

public interface OAuthClient {
    String getAccessToken(String authorizationCode);

    String getPlatformId(String accessToken);

    Auth getUserInfo(String code);
}
