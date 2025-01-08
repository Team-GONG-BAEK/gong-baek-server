package com.ggang.be.global.util;

import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class LengthValidatorTest {

    @ParameterizedTest
    @DisplayName("문자열 범위 길이 검사 테스트 - 성공케이스") // 사용 이모지 :  👪
    @ValueSource(strings = {"12345", "가나다라마", "abcde", "\uD83D\uDC6A\uD83D\uDC6A\uD83D\uDC6A\uD83D\uDC6A\uD83D\uDC6A"})
    void 문자열수_범위_계산_성공_케이스(String testCase){
        //given
        int minLength = 3;
        int maxLength = 7;
        //when & then
        Assertions.assertThat(LengthValidator.rangelengthCheck(testCase, minLength, maxLength)).isTrue();
    }

    @ParameterizedTest
    @DisplayName("문자열수 범위 길이 검사 테스트 - 실패케이스") // 사용 이모지 : 👨‍👩‍👦
    @ValueSource(strings = {"12", "12345678", "가나", "ab", "\uD83D\uDC68\u200D\uD83D\uDC69\u200D\uD83D\uDC66\uD83D\uDC68\u200D\uD83D\uDC69\u200D\uD83D\uDC66"})
    void 문자열수_범위_계산_실패_케이스(String testCase){
        //given
        int minLength = 3;
        int maxLength = 7;
        //when & then
        Assertions.assertThat(LengthValidator.rangelengthCheck(testCase, minLength, maxLength)).isFalse();
    }

    @ParameterizedTest
    @DisplayName("문자열수가 최소범위보다 같거나 큰지 - 성공케이스")//사용 이모지 : 👨‍👩‍👦
    @ValueSource(strings = {"12","123", "가나다", "abc", "\uD83D\uDC68\u200D\uD83D\uDC69\u200D\uD83D\uDC66\uD83D\uDC68\u200D\uD83D\uDC69\u200D\uD83D\uDC66"})
    void 문자열수가_최소범위보다_같거나_큰지_성공_케이스(String testCase){
        //given
        int minLength = 2;
        //when & then
        Assertions.assertThat(LengthValidator.isLongerThanMinLength(testCase, minLength)).isTrue();
    }

    @ParameterizedTest
    @DisplayName("문자열수가 최소범위보다 같거나 큰지 - 실패케이스")//사용 이모지 : 👨‍👩‍👦
    @ValueSource(strings = {"1","가", "a", " ", "\uD83D\uDC68\u200D\uD83D\uDC69\u200D\uD83D\uDC66"})
    void 문자열수가_최소범위보다_같거나_큰지_실패_케이스(String testCase){
        //given
        int minLength = 2;
        //when & then
        Assertions.assertThat(LengthValidator.isLongerThanMinLength(testCase, minLength)).isFalse();
    }

    @ParameterizedTest
    @DisplayName("문자열수가 최대범위보다 같거나 작은지 - 성공케이스")//사용 이모지 : 👨‍👩‍👦
    @ValueSource(strings = {"12", "가나", "ab", "\uD83D\uDC68\u200D\uD83D\uDC69\u200D\uD83D\uDC66\uD83D\uDC68\u200D\uD83D\uDC69\u200D\uD83D\uDC66"})
    void 문자열수가_최대범위보다_같거나_작은지_성공_케이스(String testCase){
        //given
        int maxLength = 2;
        //when & then
        Assertions.assertThat(LengthValidator.isShorterThanMaxLength(testCase, maxLength)).isTrue();
    }

    @ParameterizedTest
    @DisplayName("문자열수가 최대범위보다 같거나 작은지 - 실패케이스") //사용 이모지 : 👨‍👩‍👦
    @ValueSource(strings = {"123", "가나다", "abc", "\uD83D\uDC68\u200D\uD83D\uDC69\u200D\uD83D\uDC66\uD83D\uDC68\u200D\uD83D\uDC69\u200D\uD83D\uDC66\uD83D\uDC68\u200D\uD83D\uDC69\u200D\uD83D\uDC66"})
    void 문자열수가_최대범위보다_같거나_작은지_실패_케이스(String testCase){
        //given
        int maxLength = 2;
        //when & then
        Assertions.assertThat(LengthValidator.isShorterThanMaxLength(testCase, maxLength)).isFalse();
    }




}