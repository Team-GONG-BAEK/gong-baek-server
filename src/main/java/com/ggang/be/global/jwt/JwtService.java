package com.ggang.be.global.jwt;

import com.ggang.be.api.common.ResponseError;
import com.ggang.be.api.exception.GongBaekException;
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

        if(token == null || !token.startsWith("Bearer "))
            throw new GongBaekException(ResponseError.INVALID_TOKEN);

        try {
            String splitToken = token.split(" ")[1];
            SecretKey secretKey = Keys.hmacShaKeyFor(jwtProperties.getKey().getBytes(StandardCharsets.UTF_8));
            return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(splitToken).getPayload().get(USER_ID).toString();
        }catch (JwtException e){
            log.error("JWT parsing error : {}", e.getMessage());
            throw new GongBaekException(ResponseError.INVALID_TOKEN);
        }
    }


}
