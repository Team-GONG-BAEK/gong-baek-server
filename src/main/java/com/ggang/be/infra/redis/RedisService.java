package com.ggang.be.infra.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final StringRedisTemplate redisTemplate;

    /**
     * Redis에 key-value 데이터 저장
     */
    public void saveValue(String key, String value, Duration timeoutSeconds) {
        redisTemplate.opsForValue().set(key, value, timeoutSeconds.toSeconds(), TimeUnit.SECONDS);
    }

    /**
     * Redis에서 key에 해당하는 value 가져오기
     */
    public String getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * Redis에서 key 삭제
     */
    public void deleteValue(String key) {
        redisTemplate.delete(key);
    }

    /**
     * Redis에서 해당 key가 존재하는지 확인
     */
    public boolean checkExistsValue(String key) {
        Boolean hasKey = redisTemplate.hasKey(key);
        return hasKey != null && hasKey;
    }
}
