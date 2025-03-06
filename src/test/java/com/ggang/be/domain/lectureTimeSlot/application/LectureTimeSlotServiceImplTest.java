package com.ggang.be.domain.lectureTimeSlot.application;


import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import com.ggang.be.domain.constant.Gender;
import com.ggang.be.domain.constant.Mbti;
import com.ggang.be.domain.constant.WeekDay;
import com.ggang.be.domain.lectureTimeSlot.fixture.LectureTimeSlotFixture;
import com.ggang.be.domain.timslot.lectureTimeSlot.LectureTimeSlotEntity;
import com.ggang.be.domain.timslot.lectureTimeSlot.application.LectureTimeSlotServiceImpl;
import com.ggang.be.domain.timslot.lectureTimeSlot.vo.LectureTimeSlotVo;
import com.ggang.be.domain.timslot.lectureTimeSlot.infra.LectureTimeSlotRepository;
import com.ggang.be.domain.school.SchoolEntity;
import com.ggang.be.domain.user.UserEntity;
import com.ggang.be.domain.user.fixture.UserEntityFixture;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LectureTimeSlotServiceImplTest {

    @Mock
    private LectureTimeSlotRepository lectureTimeSlotRepository;

    @InjectMocks
    private LectureTimeSlotServiceImpl lectureTimeSlotServiceImpl;


    @Test
    void saveLectureTimeSlot() {

        //given
        UserEntity testCaseUser = UserEntity.builder()
            .school(SchoolEntity.builder().build())
            .schoolMajorName("Computer Science")
            .profileImg(1)
            .nickname("TestUser")
            .enterYear(2020)
            .mbti(Mbti.INFJ)
            .gender(Gender.MAN)
            .introduction("Hello, I am a test user.")
            .build();

        List<LectureTimeSlotVo> lectureTimeSlotVos = List.of(
            new LectureTimeSlotVo(WeekDay.MON, 9.0, 10.5),
            new LectureTimeSlotVo(WeekDay.WED, 14.0, 15.5),
            new LectureTimeSlotVo(WeekDay.FRI, 16.0, 17.0)
        );

        List<LectureTimeSlotEntity> list = lectureTimeSlotVos.stream()
            .map(lectureTimeSlotVo -> LectureTimeSlotVo.toEntity(lectureTimeSlotVo, testCaseUser))
            .toList();

        //when
        lectureTimeSlotServiceImpl.saveLectureTimeSlot(lectureTimeSlotVos, testCaseUser);

        //then

        Mockito.verify(lectureTimeSlotRepository, times(3)).save(ArgumentMatchers.any(LectureTimeSlotEntity.class));

    }


    @Test
    @DisplayName("유저가 들어갈 수 없는 시간표를 조회한다.")
    void readUserTime(){

        //given
        UserEntity userEntity = UserEntityFixture.create();

        LectureTimeSlotEntity testCase1 = LectureTimeSlotFixture.makeLectureTimeSlotEntity();
        LectureTimeSlotEntity testCase2 = LectureTimeSlotFixture.makeLectureTimeSlotEntity();
        LectureTimeSlotEntity testCase3 = LectureTimeSlotFixture.makeLectureTimeSlotEntity();

        when(lectureTimeSlotRepository.findAllByUserEntity(
            userEntity)).thenReturn(List.of(
            testCase1,
            testCase2,
            testCase3));
        //when
        List<LectureTimeSlotEntity> lectureTimeSlotEntities = lectureTimeSlotServiceImpl.readUserTime(
            userEntity);

        //then
        Assertions.assertThat(lectureTimeSlotEntities).hasSize(3)
            .containsExactlyInAnyOrder(
                testCase1,
                testCase2,
                testCase3);

    }

}