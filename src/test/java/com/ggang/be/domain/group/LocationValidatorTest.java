package com.ggang.be.domain.group;

import com.ggang.be.api.exception.GongBaekException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class LocationValidatorTest {

    private LocationValidator locationValidator;

    @BeforeEach
    void setUp() {
        locationValidator = new LocationValidator();
    }

    @Nested
    @DisplayName("장소 검증 성공 케이스")
    class SuccessCases {

        @Test
        @DisplayName("한글, 영어, 숫자, 공백, 특수문자가 포함된 정상 입력")
        void validLocation() {
            assertDoesNotThrow(() -> locationValidator.isLocationValid("학교 운동장-2번출구!"));
            assertDoesNotThrow(() -> locationValidator.isLocationValid("Gongbaek  ~"));
            assertDoesNotThrow(() -> locationValidator.isLocationValid("런닝 클럽@강남"));
        }
    }

    @Nested
    @DisplayName("장소 검증 실패 케이스")
    class FailCases {

        @Test
        @DisplayName("엔터(줄바꿈)가 포함된 입력")
        void locationWithNewLine() {
            assertThatThrownBy(() -> locationValidator.isLocationValid("학교\n운동장"))
                    .isInstanceOf(GongBaekException.class);
        }

        @Test
        @DisplayName("이모지가 포함된 입력")
        void locationWithEmoji() {
            assertThatThrownBy(() -> locationValidator.isLocationValid("강남역✨출구"))
                    .isInstanceOf(GongBaekException.class);
        }

        @Test
        @DisplayName("길이가 2자 미만인 경우")
        void tooShortLocation() {
            assertThatThrownBy(() -> locationValidator.isLocationValid("A"))
                    .isInstanceOf(GongBaekException.class);
        }

        @Test
        @DisplayName("길이가 20자 초과인 경우")
        void tooLongLocation() {
            String tooLong = "학교운동장강남역출구학교운동장강남역출구!!";
            assertThatThrownBy(() -> locationValidator.isLocationValid(tooLong))
                    .isInstanceOf(GongBaekException.class);
        }
    }
}
