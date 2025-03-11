package com.ggang.be.infra.oauth;

import com.ggang.be.api.common.ResponseError;
import com.ggang.be.api.exception.GongBaekException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Slf4j
public class AppleClientSecretGenerator {
    private final static String APPLE_TOKEN_URL = "https://appleid.apple.com";
    private final static String KID = "kid";
    private final static String ALG = "alg";

    public static String generate(AppleProperties appleProperties) {
        try {
            PrivateKey privateKey = loadPrivateKey(appleProperties.getKeyPath());

            long now = System.currentTimeMillis();
            long expireTime = now + TimeUnit.HOURS.toMillis(1); // 1시간 만료

            return Jwts.builder().setHeaderParam(ALG, "ES256") // Alg 수정
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

    private static PrivateKey loadPrivateKey(String keyPath) throws Exception {
        byte[] keyBytes;

        if (keyPath.startsWith("classpath:")) {
            String resourcePath = keyPath.replace("classpath:", "").trim();
            ClassPathResource resource = new ClassPathResource(resourcePath);

            if (!resource.exists()) {
                throw new IOException("Private key file not found in classpath: " + resourcePath);
            }

            try (InputStream inputStream = resource.getInputStream()) {
                keyBytes = inputStream.readAllBytes();
            }
        } else {
            keyBytes = Files.readAllBytes(Paths.get(keyPath));
        }

        String key = new String(keyBytes).replace("-----BEGIN PRIVATE KEY-----", "").replace("-----END PRIVATE KEY-----", "").replaceAll("\\s+", "");

        byte[] decodedKey = Base64.getDecoder().decode(key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
        return KeyFactory.getInstance("EC").generatePrivate(keySpec);
    }
}
