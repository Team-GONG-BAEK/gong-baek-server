package com.ggang.be.api.user;

import com.ggang.be.api.common.ResponseError;
import com.ggang.be.api.exception.GongBaekException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class NicknameValidatorTest {

    @ParameterizedTest
    @ValueSource(strings = {"가나aa", "bb나다", " 가나", "\n가"})
    @DisplayName("닉네임 검증 테스트 순수 한글 아닌 경우 - 실패케이스")
    void 닉네임이_순수_한글이_아닌_경우(String name){
        //when && then
        Assertions.assertThatThrownBy(() -> NicknameValidator.validate(name))
                .isInstanceOf(GongBaekException.class)
                .hasMessageContaining("닉네임은 한글로만 입력 가능합니다.")
                .hasFieldOrPropertyWithValue("responseError", ResponseError.INVALID_INPUT_NICKNAME);
    }


    @ParameterizedTest
    @ValueSource(strings = {"가나다라마바사아자", "가"})
    @DisplayName("닉네임 검증 테스트 길이가 맞지 않는 경우 - 실패케이스")
    void 닉네임의_길이범위가_맞지않는_경우(String name){
        //when && then
        Assertions.assertThatThrownBy(() -> NicknameValidator.validate(name))
            .isInstanceOf(GongBaekException.class)
            .hasMessageContaining("입력된 글자수가 허용된 범위를 벗어났습니다.")
            .hasFieldOrPropertyWithValue("responseError", ResponseError.INVALID_INPUT_LENGTH);
    }

    @ParameterizedTest
    @ValueSource(strings = {"가나다라마바사아", "가나"})
    @DisplayName("닉네임 검증 테스트 길이가 맞지 않는 경우 - 성공케이스")
    void 닉네임검증_통과의경우(String name){
        //when && then
        Assertions.assertThatCode(() -> NicknameValidator.validate(name))
            .doesNotThrowAnyException();
    }


}