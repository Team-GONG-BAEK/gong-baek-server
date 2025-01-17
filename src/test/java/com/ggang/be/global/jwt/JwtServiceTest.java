package com.ggang.be.global.jwt;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import com.ggang.be.api.user.service.UserService;
import com.ggang.be.domain.user.UserEntity;
import com.ggang.be.domain.user.fixture.UserEntityFixture;
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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class JwtServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private JwtProperties jwtProperties;

    @Spy
    @InjectMocks
    private JwtService jwtService;

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

    @Test
    @DisplayName("토큰 재발급 테스트")
    void reIssueToken() throws InterruptedException {
        //given
        String secret = "abcdefghijklmnopqrstuvwxyz123456abcdefghijklmnopqrstuvwxyz123456";
        SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        Long userId = 1L;
        UserEntity fixture = UserEntityFixture.create();

        Date date = new Date(System.currentTimeMillis() + 86400000000L);
        String token = Jwts.builder()
            .subject(userId.toString())
            .expiration(date)
            .claim("userId", userId)
            .signWith(secretKey)
            .compact();

        when(jwtProperties.getKey()).thenReturn(secret);
        when(userService.getUserById(userId)).thenReturn(fixture);
        doNothing().when(userService).validateRefreshToken(fixture, token);

       String accessToken = jwtService.createAccessToken(userId);
        String refreshToken = jwtService.createRefreshToken(userId);
        String addBearerPrefix = "Bearer " + token;

        Thread.sleep(1000);

        //when
        TokenVo tokenVo = jwtService.reIssueToken(addBearerPrefix);

        // then
       Assertions.assertThat(tokenVo.accessToken()).isNotEqualTo(accessToken);
        Assertions.assertThat(tokenVo.refreshToken()).isNotEqualTo(refreshToken);
    }
}


