package com.ggang.be.domain.user.application;

import com.ggang.be.api.email.service.AppProperties;
import com.ggang.be.api.exception.GongBaekException;
import com.ggang.be.domain.constant.Gender;
import com.ggang.be.domain.constant.Mbti;
import com.ggang.be.domain.constant.Platform;
import com.ggang.be.domain.school.SchoolEntity;
import com.ggang.be.domain.user.UserEntity;
import com.ggang.be.domain.user.dto.SaveUserSignUp;
import com.ggang.be.domain.user.infra.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AppProperties appProperties;
    
    @Mock
    private EmailValidationMetrics emailValidationMetrics;

    @InjectMocks
    private UserServiceImpl userServiceImpl;


    @Test
    @DisplayName("닉네임 중복 체크 테스트 - 통과")
    void duplicateCheckNicknamePass() {
        //given
        String nickname = "이현진";
        when(userRepository.existsUserEntitiesByNickname(nickname)).thenReturn(false);

        //when
        boolean result = userServiceImpl.duplicateCheckNickname(nickname);

        //then
        Assertions.assertThat(result).isTrue();
    }

    @Test
    @DisplayName("닉네임 중복 체크 테스트 - 실패")
    void duplicateCheckNicknameFail() {

        //given
        String nickname = "김효준";
        when(userRepository.existsUserEntitiesByNickname(nickname)).thenReturn(true);

        //when && then

        Assertions.assertThatThrownBy(() -> userServiceImpl.duplicateCheckNickname(nickname))
                .isInstanceOf(GongBaekException.class)
                .hasMessageContaining("이미 존재하는 닉네임입니다.");
    }


    @Test
    @DisplayName("회원가입 정보 저장 테스트")
    void saveUserBySignup_shouldSaveAndReturnUserEntity() {
        // Given

        SchoolEntity school = SchoolEntity.builder().schoolName("school").build();

        SaveUserSignUp request = new SaveUserSignUp(
                Platform.KAKAO,
                "dasff",
                1,
                "guswlsdl04@gmail.com",
                "nickname",
                Mbti.INFJ,
                "computer science",
                2024,
                "hello",
                Gender.MAN,
                school
        );

        UserEntity expectedUserEntity = UserEntity.builder()
                .nickname("nickname")
                .school(school)
                .gender(Gender.MAN)
                .introduction("introduction")
                .mbti(Mbti.INFJ)
                .profileImg(1)
                .enterYear(2020)
                .schoolMajorName("Computer Science")
                .build();

        when(userRepository.save(ArgumentMatchers.any(UserEntity.class))).thenReturn(expectedUserEntity);

        // When
        UserEntity result = userServiceImpl.saveUserBySignup(request);

        // Then
        Assertions.assertThat(result).isEqualTo(expectedUserEntity);
    }

    @Test
    @DisplayName("이메일 중복 체크 테스트 - 중복되지 않은 이메일")
    void checkDuplicatedEmail_shouldPassWhenEmailNotExists() {
        // Given
        String email = "test@example.com";
        when(appProperties.getAndReviewEmail()).thenReturn("review@test.com");
        when(appProperties.getIosReviewEmail()).thenReturn("ios@test.com");
        when(userRepository.existsByEmail(email)).thenReturn(false);

        // When & Then
        Assertions.assertThatCode(() -> userServiceImpl.checkDuplicatedEmail(email))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("이메일 중복 체크 테스트 - 중복된 이메일")
    void checkDuplicatedEmail_shouldThrowExceptionWhenEmailExists() {
        // Given
        String email = "duplicate@example.com";
        when(appProperties.getAndReviewEmail()).thenReturn("review@test.com");
        when(appProperties.getIosReviewEmail()).thenReturn("ios@test.com");
        when(userRepository.existsByEmail(email)).thenReturn(true);

        // When & Then
        Assertions.assertThatThrownBy(() -> userServiceImpl.checkDuplicatedEmail(email))
                .isInstanceOf(GongBaekException.class)
                .hasMessageContaining("이미 존재하는 유저입니다.");
    }

    @Test
    @DisplayName("이메일 중복 체크 테스트 - 관리자 이메일은 중복 허용")
    void checkDuplicatedEmail_shouldAllowAdminEmails() {
        // Given
        String adminEmail = "admin@test.com";
        when(appProperties.getAndReviewEmail()).thenReturn(adminEmail);
        when(appProperties.getIosReviewEmail()).thenReturn("ios@test.com");
        // 관리자 이메일은 DB 조회 없이 리턴되므로 userRepository mock 불필요

        // When & Then
        Assertions.assertThatCode(() -> userServiceImpl.checkDuplicatedEmail(adminEmail))
                .doesNotThrowAnyException();
    }

}