package com.ggang.be.infra.oauth;

import com.fasterxml.jackson.databind.JsonNode;
import com.ggang.be.api.common.ResponseError;
import com.ggang.be.api.exception.GongBaekException;
import com.ggang.be.infra.Auth;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class KakaoOAuthClient implements OAuthClient {

    private final KakaoProperties kakaoProperties;

    private final static String KAKAO_TOKEN_URL = "https://kauth.kakao.com/oauth/token";
    private final static String KAKAO_USER_INFO_URL = "https://kapi.kakao.com/v2/user/me";
    private final static String AUTHORIZATION = "Authorization";
    private final static String ACCESS_TOKEN = "access_token";
    private final static String BEARER = "Bearer ";
    private final static String AUTHORIZATION_TOKEN = "authorization_code";

    public Auth getUserInfo(String accessToken) {
        log.info("카카오 OAuth 검증 요청 시작");

        HttpHeaders headers = new HttpHeaders();
        headers.set(AUTHORIZATION, BEARER + accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<JsonNode> response = restTemplate.exchange(
                KAKAO_USER_INFO_URL, HttpMethod.GET, entity, JsonNode.class);

        if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
            throw new GongBaekException(ResponseError.INVALID_KAKAO_ACCESS_TOKEN);
        }

        log.info("카카오 로그인 응답: {}", response.getBody());
        return new Auth(response.getBody().get("id").asText());
    }

    public String getAccessToken(String code) {
        log.info("카카오 accessToken 요청 시작");

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", AUTHORIZATION_TOKEN);
        params.add("client_id", kakaoProperties.getClientId());
        params.add("redirect_uri", kakaoProperties.getRedirectUri());
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(
                    KAKAO_TOKEN_URL, HttpMethod.POST, request, Map.class);

            if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
                throw new GongBaekException(ResponseError.INVALID_KAKAO_ACCESS_TOKEN);
            }

            return response.getBody().get(ACCESS_TOKEN).toString();
        } catch (Exception e) {
            throw new GongBaekException(ResponseError.INVALID_KAKAO_ACCESS_TOKEN);
        }
    }

    @Override
    public String getPlatformId(String code) {
        return getUserInfo(code).platformId();
    }
}
