package com.ggang.be.global.jwt;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
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

    public String createAccessToken(final Long userId){
        SecretKey secretKey = Keys.hmacShaKeyFor(jwtProperties.getKey().getBytes(StandardCharsets.UTF_8));
        return  Jwts.builder()
            .subject(userId.toString())
            .expiration(new Date(System.currentTimeMillis() + jwtProperties.getAccessExpiration()))
            .claim(USER_ID, userId)
            .signWith(secretKey)
            .compact();
    }

    public String createRefreshToken(final Long userId){
        SecretKey secretKey = Keys.hmacShaKeyFor(jwtProperties.getKey().getBytes(StandardCharsets.UTF_8));
        return  Jwts.builder()
            .subject(userId.toString())
            .expiration(new Date(System.currentTimeMillis() + jwtProperties.getRefreshExpiration()))
            .claim(USER_ID, userId)
            .signWith(secretKey)
            .compact();
    }

    public String parseTokenAndGetUserId(String token){
        try {
            SecretKey secretKey = Keys.hmacShaKeyFor(jwtProperties.getKey().getBytes(StandardCharsets.UTF_8));
            return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get(USER_ID).toString();
        }catch (JwtException e){
            log.error("JWT parsing error : {}", e.getMessage());
            throw e;
        }
    }


}
