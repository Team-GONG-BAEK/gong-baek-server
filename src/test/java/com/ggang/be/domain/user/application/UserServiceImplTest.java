package com.ggang.be.domain.user.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.ggang.be.api.exception.GongBaekException;
import com.ggang.be.domain.user.infra.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

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
}