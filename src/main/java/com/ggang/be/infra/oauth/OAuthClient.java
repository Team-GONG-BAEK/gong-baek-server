package com.ggang.be.infra.oauth;

import com.ggang.be.infra.Auth;

public interface OAuthClient {
    String getAccessToken(String authorizationCode);

    String getPlatformId(String accessToken);

    Auth getUserInfo(String code);
}
