package com.ggang.be.global.jwt;

import com.ggang.be.api.common.ResponseError;
import com.ggang.be.api.exception.GongBaekException;
import com.ggang.be.api.user.service.UserService;
import com.ggang.be.domain.user.UserEntity;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtService {

    private static final String USER_ID = "userId";
    private final JwtProperties jwtProperties;
    private final UserService userService;

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
        return buildRefreshToken(userId, secretKey);
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
        if (token == null || !token.startsWith("Bearer ")) {
            throw new GongBaekException(ResponseError.INVALID_TOKEN);
        }

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
