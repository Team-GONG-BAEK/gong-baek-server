package com.ggang.be.global.jwt;

import com.ggang.be.api.common.ResponseError;
import com.ggang.be.api.exception.GongBaekException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.micrometer.common.util.StringUtils;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Objects;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtService {

    private static final String USER_ID = "userId";
    private final JwtProperties jwtProperties;

    public String createAccessToken(final Long userId) {
        SecretKey secretKey = getSecretKey();
        return Jwts.builder()
            .subject(userId.toString())
            .expiration(new Date(System.currentTimeMillis() + jwtProperties.getAccessExpiration()))
            .claim(USER_ID, userId)
            .signWith(secretKey)
            .compact();
    }

    public String createRefreshToken(final Long userId) {
        SecretKey secretKey = getSecretKey();
        return Jwts.builder()
            .subject(userId.toString())
            .expiration(new Date(System.currentTimeMillis() + jwtProperties.getRefreshExpiration()))
            .claim(USER_ID, userId)
            .signWith(secretKey)
            .compact();
    }

    public Long parseTokenAndGetUserId(String token) {
        isValidToken(token);

        try {
            String splitToken = token.split(" ")[1];
            SecretKey secretKey = getSecretKey();
            return Long.parseLong(parseTokenAndGetUserId(secretKey, splitToken));
        } catch (JwtException | NumberFormatException e) {
            log.error("JWT parsing error : {}", e.getMessage());
            throw new GongBaekException(ResponseError.INVALID_TOKEN);
        }

    }

    private String parseTokenAndGetUserId(SecretKey secretKey, String splitToken) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(splitToken)
            .getPayload().get(USER_ID).toString();
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(
            jwtProperties.getKey().getBytes(StandardCharsets.UTF_8));
    }


    public void isValidToken(String token) {
        if (token == null || !token.startsWith("Bearer "))
            throw new GongBaekException(ResponseError.INVALID_TOKEN);

        try {
            String splitToken = token.split(" ")[1];
            SecretKey secretKey = getSecretKey();

            Long.parseLong(parseTokenAndGetUserId(secretKey, splitToken));
        } catch (JwtException | NumberFormatException e) {
            log.error("JWT parsing error : {}", e.getMessage());
            throw new GongBaekException(ResponseError.INVALID_TOKEN);
        }
    }
}
