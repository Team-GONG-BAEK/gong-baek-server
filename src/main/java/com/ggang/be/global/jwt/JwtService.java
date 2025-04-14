package com.ggang.be.global.jwt;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ggang.be.api.common.ResponseError;
import com.ggang.be.api.exception.GongBaekException;
import com.ggang.be.api.user.service.UserService;
import com.ggang.be.domain.user.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtService {
    private static final String USER_ID = "userId";
    private static final String PLATFROM_ID = "platformId";
    private static final String BEARER = "Bearer ";
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final JwtProperties jwtProperties;
    private final UserService userService;

    public String createAccessToken(final Long userId) {
        SecretKey secretKey = getSecretKey();

        long expirationMillis = jwtProperties.getAccessExpiration();
        log.info("[JwtService] access token expirationMillis: {}", expirationMillis);

        return Jwts.builder()
            .subject(userId.toString())
            .expiration(new Date(System.currentTimeMillis() + jwtProperties.getAccessExpiration()))
            .claim(USER_ID, userId)
            .signWith(secretKey)
            .compact();
    }

    public String createRefreshToken(final Long userId) {
        SecretKey secretKey = getSecretKey();
        return buildRefreshToken(userId, secretKey);
    }

    public String createTempAccessToken(final String platformId) {
        SecretKey secretKey = getSecretKey();
        return Jwts.builder()
                .subject(platformId)
                .expiration(new Date(System.currentTimeMillis() + jwtProperties.getAccessExpiration()))
                .claim(PLATFROM_ID, platformId)
                .signWith(secretKey)
                .compact();
    }

    public String createTempRefreshToken(final String platformId) {
        SecretKey secretKey = getSecretKey();
        return Jwts.builder()
                .subject(platformId)
                .expiration(new Date(System.currentTimeMillis() + jwtProperties.getRefreshExpiration()))
                .claim(PLATFROM_ID, platformId)
                .signWith(secretKey)
                .compact();
    }


    private String buildRefreshToken(Long userId, SecretKey secretKey) {
        return Jwts.builder()
            .subject(userId.toString())
            .expiration(new Date(System.currentTimeMillis() + jwtProperties.getRefreshExpiration()))
            .claim(USER_ID, userId)
            .signWith(secretKey)
            .compact();
    }

    @Transactional
    public TokenVo reIssueToken(final String refreshToken) {
        Long userId = parseTokenAndGetUserId(refreshToken);
        UserEntity findUser = userService.getUserById(userId);
        String extractPrefixToken = refreshToken.split(" ")[1];
        isValidRefreshToken(findUser, extractPrefixToken);
        String renewRefreshToken = createRefreshToken(userId);
        findUser.updateRefreshToken(renewRefreshToken);

        return TokenVo.of(userId, createAccessToken(userId), renewRefreshToken);
    }

    private void isValidRefreshToken(UserEntity findUser, String refreshToken) {
        userService.validateRefreshToken(findUser, refreshToken);
    }


    public Long parseTokenAndGetUserId(String token) {
        isValidToken(token);

        try {
            String splitToken = token.split(" ")[1];
            SecretKey secretKey = getSecretKey();
            Long userId = parseTokenAndGetUserId(secretKey, splitToken);

            if (userService.getUserById(userId).getRefreshToken() == null) {
                throw new GongBaekException(ResponseError.INVALID_TOKEN);
            }
            return userId;
        } catch (JwtException | NumberFormatException e) {
            if (e instanceof ExpiredJwtException) {
                log.error("JWT expired: {}", e.getMessage());
                throw new GongBaekException(ResponseError.EXPIRED_TOKEN);
            }

            log.error("Invalid JWT: {}", e.getMessage());
            throw new GongBaekException(ResponseError.INVALID_TOKEN);
        }
    }

    private Long parseTokenAndGetUserId(SecretKey secretKey, String token) {
        Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        if (!claims.containsKey(USER_ID)) {
            log.error("잘못된 accessToken: userId 없음.");
            throw new GongBaekException(ResponseError.INVALID_TOKEN);
        }

        try {
            Object userIdObject = claims.get(USER_ID);
            if (userIdObject instanceof Number) {
                return ((Number) userIdObject).longValue();
            } else {
                throw new GongBaekException(ResponseError.INVALID_TOKEN);
            }
        } catch (Exception e) {
            log.error("userId 변환 오류: {}", e.getMessage());
            throw new GongBaekException(ResponseError.INVALID_TOKEN);
        }
    }


    public String extractPlatformUserIdFromToken(String token) {
        try {
            String splitToken = token.split(" ")[1];
            SecretKey secretKey = getSecretKey();
            return parseTokenAndGetPlatformUserId(secretKey, splitToken);
        } catch (JwtException e) {
            log.error("JWT parsing error: {}", e.getMessage());
            throw new GongBaekException(ResponseError.INVALID_TOKEN);
        }
    }

    private String parseTokenAndGetPlatformUserId(SecretKey secretKey, String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get(PLATFROM_ID)
                .toString();
    }


    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(
            jwtProperties.getKey().getBytes(StandardCharsets.UTF_8));
    }


    public void isValidToken(String token) {
        if (token == null || !token.startsWith(BEARER)) {
            throw new GongBaekException(ResponseError.INVALID_TOKEN);
        }

        try {
            String splitToken = token.split(" ")[1];
            SecretKey secretKey = getSecretKey();

            parseTokenAndGetUserId(secretKey, splitToken);
        } catch (JwtException | NumberFormatException e) {
            if (e instanceof ExpiredJwtException) {
                log.error("JWT expired: {}", e.getMessage());
                throw new GongBaekException(ResponseError.EXPIRED_TOKEN);
            }

            log.error("Invalid JWT: {}", e.getMessage());
            throw new GongBaekException(ResponseError.INVALID_TOKEN);
        }
    }

    public static JsonNode parseJson(String jsonString) {
        try {
            return objectMapper.readTree(jsonString);
        } catch (Exception e) {
            log.error("JSON 파싱 실패", e);
            throw new IllegalArgumentException("JSON 파싱에 실패했습니다.");
        }
    }
}
