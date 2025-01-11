package com.ggang.be.global.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class JwtServiceTest {

    @Test
    @DisplayName("jwt 토큰 생성 및 파싱 테스트")
    void jwtTest(){

        //given
        String secret = "1123412341234123412341234123412341234123412341234";
        SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        Long userId = 1L;

        String token = Jwts.builder()
            .subject(userId.toString())
            .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
            .claim("userId", userId)
            .signWith(secretKey)
            .compact();

            //when && then
            Jws<Claims> x = Jwts.parser().verifyWith(secretKey).build()
                    .parseSignedClaims(token);
                String parsedUserId = x.getPayload().get("userId").toString();
        Date expiration = x.getPayload().getExpiration();
        Assertions.assertThat(expiration).isAfter(new Date());
        Assertions.assertThat(Long.parseLong(parsedUserId)).isEqualTo(userId);
        }
    }


