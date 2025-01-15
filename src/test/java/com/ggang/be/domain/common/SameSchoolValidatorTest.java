package com.ggang.be.domain.common;

import com.ggang.be.api.exception.GongBaekException;
import com.ggang.be.domain.group.everyGroup.EveryGroupEntity;
import com.ggang.be.domain.group.everyGroup.EveryGroupFixture;
import com.ggang.be.domain.group.onceGroup.OnceGroupEntity;
import com.ggang.be.domain.group.onceGroup.OnceGroupFixture;
import com.ggang.be.domain.school.SchoolEntity;
import com.ggang.be.domain.school.fixture.SchoolEntityFixture;
import com.ggang.be.domain.user.UserEntity;
import com.ggang.be.domain.user.fixture.UserEntityFixture;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SameSchoolValidatorTest {

    private SameSchoolValidator sameSchoolValidator = new SameSchoolValidator();

    @Test
    @DisplayName("내가 주차 그룹을 조회할 때 다른 학교인 경우")
    void isUserReadDiffSchoolEveryGroup() {
        //given
        SchoolEntity test = SchoolEntityFixture.createSchoolName("광운대학교");
        SchoolEntity test2 = SchoolEntityFixture.createSchoolName("test2");
        UserEntity fixtureUser = UserEntityFixture.createBySchool(test);
        EveryGroupEntity testEveryGroup = EveryGroupFixture.createBySchool(test2);

        //when && then
        Assertions.assertThatThrownBy(() -> sameSchoolValidator.isUserReadMySchoolEveryGroup(fixtureUser, testEveryGroup))
            .isInstanceOf(GongBaekException.class)
            .hasMessageContaining("같은 학교의 모임만 조회 가능합니다.");
    }

    @Test
    @DisplayName("내 일회성 그룹 조회할 때 다른 학교인 경우")
    void isUserReadDiffSchoolOnceGroup() {
        //given
        SchoolEntity test = SchoolEntityFixture.createSchoolName("광운대학교");
        SchoolEntity test2 = SchoolEntityFixture.createSchoolName("test2");
        UserEntity fixtureUser = UserEntityFixture.createBySchool(test);
        OnceGroupEntity testOnceGroup = OnceGroupFixture.createBySchool(test2);

        //when && then
        Assertions.assertThatThrownBy(() -> sameSchoolValidator.isUserReadMySchoolOnceGroup(fixtureUser, testOnceGroup))
            .isInstanceOf(GongBaekException.class)
            .hasMessageContaining("같은 학교의 모임만 조회 가능합니다.");
    }

    @Test
    @DisplayName("내 일회성 그룹 조회할 때 같은 학교인 경우")
    void isUserReadSameSchoolEveryGroup() {
        //given
        SchoolEntity test = SchoolEntityFixture.createSchoolName("test2");
        SchoolEntity test2 = SchoolEntityFixture.createSchoolName("test2");
        UserEntity fixtureUser = UserEntityFixture.createBySchool(test);
        EveryGroupEntity testEveryGroup = EveryGroupFixture.createBySchool(test2);

        //when && then
        Assertions.assertThatCode(() -> sameSchoolValidator.isUserReadMySchoolEveryGroup(fixtureUser, testEveryGroup))
            .doesNotThrowAnyException();
    }


    @Test
    @DisplayName("내 일회성 그룹 조회할 때 다른 학교인 경우")
    void isUserReadSameSchoolOnceGroup() {
        //given
        SchoolEntity test = SchoolEntityFixture.createSchoolName("test2");
        SchoolEntity test2 = SchoolEntityFixture.createSchoolName("test2");
        UserEntity fixtureUser = UserEntityFixture.createBySchool(test);
        OnceGroupEntity testOnceGroup = OnceGroupFixture.createBySchool(test2);

        //when && then
        Assertions.assertThatCode(() -> sameSchoolValidator.isUserReadMySchoolOnceGroup(fixtureUser, testOnceGroup))
            .doesNotThrowAnyException();
    }
}