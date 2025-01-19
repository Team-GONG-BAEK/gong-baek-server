package com.ggang.be.global.util;


import java.time.LocalTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TimeConverterTest {

    @Test
    @DisplayName("double 형 시간을 LocalTime으로 변환 - ~시 30분")
    void toLocalTimeIncludeHalfHour() {

        //given
        double time = 10.5;

        //when
        LocalTime localTime = TimeConverter.toLocalTime(time);

        //then
        Assertions.assertThat(localTime.getHour()).isEqualTo((int) time);
        Assertions.assertThat(localTime.getMinute()).isEqualTo(30);

    }


    @Test
    @DisplayName("double 형 시간을 LocalTime으로 변환 - ~시")
    void toLocalTimeIncludeoClock() {
        //given
        double time = 10;

        //when
        LocalTime localTime = TimeConverter.toLocalTime(time);

        //then
        Assertions.assertThat(localTime.getHour()).isEqualTo((int) time);
        Assertions.assertThat(localTime.getMinute()).isEqualTo(0);
    }
}