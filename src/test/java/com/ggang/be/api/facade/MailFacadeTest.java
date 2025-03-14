package com.ggang.be.api.facade;

import com.ggang.be.api.common.ResponseError;
import com.ggang.be.api.email.service.EmailProperties;
import com.ggang.be.api.email.service.MailService;
import com.ggang.be.api.exception.GongBaekException;
import com.ggang.be.api.school.service.SchoolService;
import com.ggang.be.api.user.service.UserService;
import com.ggang.be.infra.redis.RedisService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MailFacadeTest {

    @Mock
    private EmailProperties emailProperties;

    @Mock
    private UserService userService;

    @Mock
    private MailService mailService;

    @Mock
    private SchoolService schoolService;

    @Mock
    private RedisService redisService;

    @InjectMocks
    private MailFacade mailFacade;

    private final Map<String, String> schoolDomainMap = new HashMap<>();

    @BeforeEach
    void setUp() {
        // 학교 이름에 대한 실제 도메인 매핑
        schoolDomainMap.put("홍익대학교", "hongik");
        schoolDomainMap.put("가톨릭대학교", "catholic");
        schoolDomainMap.put("건국대학교", "konkuk");
    }

    @Test
    void 이메일_도메인이_정상적으로_매칭되는_경우_1() {
        String email = "guswlsdl04@g.hongik.ac.kr";
        String schoolName = "홍익대학교";
        String authCode = "123456";

        // Mock 설정
        when(schoolService.findSchoolDomainByName(schoolName)).thenReturn(schoolDomainMap.get(schoolName));
        when(redisService.getValue("AuthCode " + email)).thenReturn(authCode);
        when(redisService.checkExistsValue("AuthCode " + email)).thenReturn(true);

        // 정상 검증 테스트 (예외가 발생하지 않아야 함)
        mailFacade.verifiedCode(email, schoolName, authCode);
    }

    @Test
    void 이메일_도메인이_정상적으로_매칭되는_경우_2() {
        String email = "guswlsdl04@konkuk.ac.kr";
        String schoolName = "건국대학교";
        String authCode = "123456";

        // Mock 설정
        when(schoolService.findSchoolDomainByName(schoolName)).thenReturn(schoolDomainMap.get(schoolName));
        when(redisService.getValue("AuthCode " + email)).thenReturn(authCode);
        when(redisService.checkExistsValue("AuthCode " + email)).thenReturn(true);

        // 정상 검증 테스트 (예외가 발생하지 않아야 함)
        mailFacade.verifiedCode(email, schoolName, authCode);
    }

    @Test
    void 이메일_도메인이_정상적으로_매칭되는_경우_3() {
        String email = "guswlsdl04@catholic.ac.kr";
        String schoolName = "가톨릭대학교";
        String authCode = "123456";

        // Mock 설정
        when(schoolService.findSchoolDomainByName(schoolName)).thenReturn(schoolDomainMap.get(schoolName));
        when(redisService.getValue("AuthCode " + email)).thenReturn(authCode);
        when(redisService.checkExistsValue("AuthCode " + email)).thenReturn(true);

        // 정상 검증 테스트 (예외가 발생하지 않아야 함)
        mailFacade.verifiedCode(email, schoolName, authCode);
    }

    @Test
    void 이메일_도메인이_일치하지_않는_경우_예외발생() {
        String email = "guswlsdl04@wrong.ac.kr";
        String schoolName = "가톨릭대학교";
        String authCode = "123456";

        // Mock 설정
        when(schoolService.findSchoolDomainByName(schoolName)).thenReturn(schoolDomainMap.get(schoolName));

        // 검증 실패 시 예외 발생
        assertThatThrownBy(() -> mailFacade.verifiedCode(email, schoolName, authCode))
                .isInstanceOf(GongBaekException.class)
                .hasMessageContaining(ResponseError.INVALID_EMAIL_DOMAIN.getMessage());
    }

    @Test
    void 이메일_코드가_일치하지_않는_경우_예외발생() {
        String email = "guswlsdl04@catholic.ac.kr";
        String schoolName = "가톨릭대학교";
        String authCode = "123456";
        String wrongAuthCode = "654321";

        // Mock 설정
        when(schoolService.findSchoolDomainByName(schoolName)).thenReturn(schoolDomainMap.get(schoolName));
        when(redisService.getValue("AuthCode " + email)).thenReturn(wrongAuthCode);
        when(redisService.checkExistsValue("AuthCode " + email)).thenReturn(true);

        // 인증 코드 불일치 예외 테스트
        assertThatThrownBy(() -> mailFacade.verifiedCode(email, schoolName, authCode))
                .isInstanceOf(GongBaekException.class)
                .hasMessageContaining(ResponseError.INVALID_INPUT_VALUE.getMessage());
    }

    @Test
    void 존재하지_않는_학교_입력시_예외발생() {
        String email = "guswlsdl04@random.ac.kr";
        String schoolName = "없는대학교";
        String authCode = "123456";

        // Mock 설정
        when(schoolService.findSchoolDomainByName(schoolName)).thenReturn(null);

        // 존재하지 않는 학교 예외 발생
        assertThatThrownBy(() -> mailFacade.verifiedCode(email, schoolName, authCode))
                .isInstanceOf(GongBaekException.class)
                .hasMessageContaining(ResponseError.INVALID_EMAIL_DOMAIN.getMessage());
    }
}
