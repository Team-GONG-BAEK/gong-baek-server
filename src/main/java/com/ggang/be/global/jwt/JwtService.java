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
    private static final String PLATFORM_ID = "platformId";
    private static final String BEARER = "Bearer ";
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final JwtProperties jwtProperties;
    private final UserService userService;

    public String createAccessToken(final Long userId) {
        return buildToken(userId.toString(), jwtProperties.getAccessExpiration(), USER_ID, userId);
    }

    public String createRefreshToken(final Long userId) {
        return buildToken(userId.toString(), jwtProperties.getRefreshExpiration(), USER_ID, userId);
    }

    public String createTempAccessToken(final String platformId) {
        return buildToken(platformId, jwtProperties.getAccessExpiration(), PLATFORM_ID, platformId);
    }

    public String createTempRefreshToken(final String platformId) {
        return buildToken(platformId, jwtProperties.getRefreshExpiration(), PLATFORM_ID, platformId);
    }

    private String buildToken(String subject, long expiration, String claimKey, Object claimValue) {
        SecretKey secretKey = getSecretKey();

        long expirationMillis = jwtProperties.getAccessExpiration();
        log.info("[JwtService] access token expirationMillis: {}", expirationMillis);

        return Jwts.builder()
                .subject(subject)
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .claim(claimKey, claimValue)
                .signWith(secretKey)
                .compact();
    }

    @Transactional
    public TokenVo reIssueToken(final String refreshToken) {
        isValidRefreshToken(refreshToken);
        Long userId = parseRefreshTokenAndGetUserId(refreshToken);
        UserEntity user = userService.getUserById(userId);

        String newRefreshToken = createRefreshToken(userId);
        user.updateRefreshToken(newRefreshToken);

        return TokenVo.of(userId, createAccessToken(userId), newRefreshToken);
    }

    public Long parseTokenAndGetUserId(String token) {
        return parseToken(token, false);
    }

    public Long parseRefreshTokenAndGetUserId(String token) {
        return parseToken(token, true);
    }

    private Long parseToken(String bearerToken, boolean isRefreshToken) {
        String token = extractToken(bearerToken);
        try {
            Claims claims = extractClaims(token);
            return extractUserIdFromClaims(claims);
        } catch (ExpiredJwtException e) {
            log.error("JWT expired ({}): {}", isRefreshToken ? "refresh" : "access", e.getMessage());
            throw new GongBaekException(isRefreshToken ? ResponseError.EXPIRED_REFRESH_TOKEN : ResponseError.EXPIRED_TOKEN);
        } catch (JwtException | IllegalArgumentException e) {
            log.error("JWT invalid ({}): {}", isRefreshToken ? "refresh" : "access", e.getMessage());
            throw new GongBaekException(ResponseError.INVALID_TOKEN);
        }
    }

    public void isValidAccessToken(String token) {
        validateJwt(token, false);
    }

    public void isValidRefreshToken(String token) {
        validateJwt(token, true);
    }

    private void validateJwt(String bearerToken, boolean isRefreshToken) {
        String token = extractToken(bearerToken);
        try {
            extractClaims(token); // 파싱만으로 유효성 검증
        } catch (ExpiredJwtException e) {
            log.error("JWT expired ({}): {}", isRefreshToken ? "refresh" : "access", e.getMessage());
            throw new GongBaekException(isRefreshToken ? ResponseError.EXPIRED_REFRESH_TOKEN : ResponseError.EXPIRED_TOKEN);
        } catch (JwtException | IllegalArgumentException e) {
            log.error("JWT invalid ({}): {}", isRefreshToken ? "refresh" : "access", e.getMessage());
            throw new GongBaekException(ResponseError.INVALID_TOKEN);
        }
    }

    public String extractPlatformUserIdFromToken(String bearerToken) {
        try {
            String token = extractToken(bearerToken);
            Claims claims = extractClaims(token);
            return claims.get(PLATFORM_ID).toString();
        } catch (JwtException e) {
            log.error("Platform ID 토큰 파싱 실패: {}", e.getMessage());
            throw new GongBaekException(ResponseError.INVALID_TOKEN);
        }
    }

    private String extractToken(String bearerToken) {
        if (bearerToken == null || !bearerToken.startsWith(BEARER)) {
            throw new GongBaekException(ResponseError.INVALID_TOKEN);
        }
        return bearerToken.substring(BEARER.length());
    }

    private Claims extractClaims(String token) {
        SecretKey secretKey = getSecretKey();
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private Long extractUserIdFromClaims(Claims claims) {
        try {
            Object userId = claims.get(USER_ID);
            if (userId instanceof Number) {
                return ((Number) userId).longValue();
            } else {
                return Long.parseLong(userId.toString());
            }
        } catch (Exception e) {
            log.error("userId 추출 실패: {}", e.getMessage());
            throw new GongBaekException(ResponseError.INVALID_TOKEN);
        }
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(
            jwtProperties.getKey().getBytes(StandardCharsets.UTF_8));
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
