# 이메일 중복 검사 성능 최적화 가이드

## 문제 상황
이메일 인증 코드 발송 시 이메일 중복 검사가 느린 문제가 발생했습니다.

### 기존 문제점
1. **인덱스 부족**: `email` 컬럼에 인덱스가 없어 Full Table Scan 발생
2. **비효율적 쿼리**: 중복 검사만 하면 되는데 전체 엔티티 조회 (`findByEmail`)
3. **중복 호출**: 인증 코드 발송과 검증 시 두 번 중복 검사 실행

## 개선 방안

### 1. 데이터베이스 레벨 최적화

#### 인덱스 추가
```java
@Table(indexes = {
    @Index(name = "user_nickname_index", columnList = "nickname"),
    @Index(name = "user_email_index", columnList = "email")  // 추가
})
```

**효과**: 
- 이메일 검색 시 O(log n) 시간 복잡도로 성능 향상
- Full Table Scan 방지

### 2. 쿼리 최적화

#### 기존 방식
```java
// 전체 엔티티 조회 (비효율적)
Optional<UserEntity> findByEmail(String email);
```

#### 개선 방식
```java
// 존재 여부만 확인 (효율적)
boolean existsByEmail(String email);
```

**효과**:
- 메모리 사용량 감소
- 네트워크 트래픽 감소
- 쿼리 실행 시간 단축

### 3. 애플리케이션 레벨 캐싱

```java
@Cacheable(value = "emailDuplicateCheck", key = "#email", unless = "#result == false")
public void checkDuplicatedEmail(String email) {
    // 구현 로직
}
```

**효과**:
- 동일한 이메일에 대한 반복 검사 시 캐시 히트로 성능 향상
- 데이터베이스 부하 감소

## 성능 개선 결과 예상

### Before (기존)
- 쿼리 시간: ~50-100ms (Full Table Scan)
- 메모리 사용량: 전체 UserEntity 객체
- 캐시: 없음

### After (개선 후)
- 쿼리 시간: ~1-5ms (인덱스 사용)
- 메모리 사용량: boolean 값만
- 캐시: 동일 이메일 재검사 시 ~0.1ms

## 추가 고려사항

### 1. 비동기 처리 개선
```java
@Async("emailTaskExecutor")
public CompletableFuture<Void> sendCodeToEmailAsync(String email, String schoolName) {
    // 비동기로 이메일 발송 처리
}
```

### 2. 배치 처리 최적화
여러 이메일을 한번에 검사하는 경우:
```java
@Query("SELECT email FROM user WHERE email IN :emails")
List<String> findExistingEmails(@Param("emails") List<String> emails);
```

### 3. Redis 캐시 도입 (선택사항)
더 고성능이 필요한 경우:
```java
@Cacheable(value = "emailDuplicateCheck", key = "#email")
@CacheEvict(value = "emailDuplicateCheck", key = "#email", condition = "#result == true")
```

## 모니터링 및 측정

### 성능 측정 포인트
1. 쿼리 실행 시간
2. 캐시 히트율
3. 메모리 사용량
4. 데이터베이스 커넥션 풀 상태

### 로그 추가
```java
@Slf4j
public class EmailDuplicateCheckService {
    public void checkDuplicatedEmail(String email) {
        long startTime = System.currentTimeMillis();
        // 중복 검사 로직
        long endTime = System.currentTimeMillis();
        log.info("Email duplicate check completed in {}ms for email: {}", 
                 endTime - startTime, email);
    }
}
```

## 결론
위 최적화를 통해 **90% 이상의 성능 향상**을 기대할 수 있으며, 특히 동일한 이메일에 대한 반복 검사 시에는 **99% 이상의 성능 향상**을 달성할 수 있습니다.