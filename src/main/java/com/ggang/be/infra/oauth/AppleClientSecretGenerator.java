package com.ggang.be.infra.oauth;

import com.ggang.be.api.common.ResponseError;
import com.ggang.be.api.exception.GongBaekException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static io.jsonwebtoken.Jwts.SIG.ES256;

@Slf4j
public class AppleClientSecretGenerator {
    private final static String APPLE_TOKEN_URL = "https://appleid.apple.com";
    private final static String KID = "kid";
    private final static String ALG = "alg";

    public static String generate(AppleProperties appleProperties) {

        try {
            byte[] keyBytes = Files.readAllBytes(Paths.get(appleProperties.getKeyPath()));
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
            PrivateKey privateKey = KeyFactory.getInstance("EC").generatePrivate(keySpec);

            long now = System.currentTimeMillis();
            long expireTime = System.currentTimeMillis() + TimeUnit.DAYS.toMillis(180);

            return Jwts.builder()
                    .setHeaderParam(ALG, ES256)
                    .setHeaderParam(KID, appleProperties.getLoginKey())
                    .setIssuer(appleProperties.getTeamId())
                    .setIssuedAt(new Date(now))
                    .setExpiration(new Date(expireTime))
                    .setAudience(APPLE_TOKEN_URL)
                    .setSubject(appleProperties.getClientId())
                    .signWith(privateKey, SignatureAlgorithm.ES256)
                    .compact();
        } catch (Exception e) {
            log.error("Apple Client Secret 생성 실패", e);
            throw new GongBaekException(ResponseError.APPLE_SECRET_GENERATION_FAILED);
        }
    }
}
