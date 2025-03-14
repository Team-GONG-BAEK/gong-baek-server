package com.ggang.be.infra.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.ggang.be.api.common.ResponseError;
import com.ggang.be.api.exception.GongBaekException;
import com.ggang.be.global.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
@RequiredArgsConstructor
public class TokenParser {

    private final JwtService jwtService;

    public String decodePayload(String idToken) {
        String[] tokenParts = idToken.split("\\.");
        if (tokenParts.length < 2) {
            throw new GongBaekException(ResponseError.INVALID_APPLE_ID_TOKEN);
        }
        return new String(Base64.getUrlDecoder().decode(tokenParts[1]));
    }

    public JsonNode parseJson(String payload) {
        return jwtService.parseJson(payload);
    }
}

