package com.ggang.be.infra.oauth;

import com.fasterxml.jackson.databind.JsonNode;
import com.ggang.be.api.common.ResponseError;
import com.ggang.be.api.exception.GongBaekException;
import com.ggang.be.infra.Auth;
import com.ggang.be.infra.service.TokenParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class AppleOAuthClient implements OAuthClient {

    private final AppleProperties appleProperties;
    private final TokenParser tokenParser;

    private final static String APPLE_TOKEN_URL = "https://appleid.apple.com/auth/token";
    private final static String CLIENT_ID = "client_id";
    private final static String CLIENT_SECRET = "client_secret";
    private final static String GRANT_TYPE = "grant_type";
    private final static String AUTHORIZATION_TOKEN = "authorization_code";
    private final static String CODE = "code";
    private final static String REDIRECT_URI = "redirect_uri";
    private final static String ID_TOKEN = "id_token";

    private final RestClient restClient = RestClient.create();

    public String getAccessToken(String authorizationCode) {
        log.info("애플 OAuth 토큰 요청 시작");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add(GRANT_TYPE, AUTHORIZATION_TOKEN);
        params.add(CLIENT_ID, appleProperties.getClientId());
        params.add(CLIENT_SECRET, generateClientSecret());
        params.add(CODE, authorizationCode);
        params.add(REDIRECT_URI, appleProperties.getRedirectUri());

        try {
            Map response = restClient.post()
                    .uri(APPLE_TOKEN_URL)
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(params)
                    .retrieve()
                    .body(Map.class);

            if (response == null || !response.containsKey(ID_TOKEN)) {
                throw new GongBaekException(ResponseError.INVALID_APPLE_ID_TOKEN);
            }


            log.info("애플 로그인 응답: {}", response);
            return response.get(ID_TOKEN).toString();
        } catch (Exception e) {
            log.error("애플 액세스 토큰 요청 실패", e);
            throw new GongBaekException(ResponseError.INVALID_APPLE_ID_TOKEN);
        }
    }

    @Override
    public String getPlatformId(String code) {
        return getUserInfo(code).platformId();
    }

    @Override
    public Auth getUserInfo(String idToken) {
        log.info("애플 idToken 검증 및 디코딩 시작");

        try {
            String[] tokenParts = idToken.split("\\.");
            if (tokenParts.length < 2) {
                log.error("잘못된 애플 idToken 형식: {}", idToken);
                throw new GongBaekException(ResponseError.INVALID_APPLE_ID_TOKEN);
            }

            String payload = tokenParser.decodePayload(idToken);
            JsonNode jsonNode = tokenParser.parseJson(payload);

            if (!jsonNode.has("sub")) {
                log.error("애플 idToken에 sub 없음");
                throw new GongBaekException(ResponseError.APPLE_ID_TOKEN_MISSING_SUB);
            }

            String appleId = jsonNode.get("sub").asText();
            log.info("애플 로그인 성공 - Apple ID: {}", appleId);

            return new Auth(appleId);
        } catch (Exception e) {
            log.error("애플 idToken 디코딩 실패", e);
            throw new GongBaekException(ResponseError.APPLE_ID_TOKEN_DECODING_FAILED);
        }
    }

    private String generateClientSecret() {
        return AppleClientSecretGenerator.generate(appleProperties);
    }
}
