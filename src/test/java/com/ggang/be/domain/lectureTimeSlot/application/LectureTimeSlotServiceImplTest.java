package com.ggang.be.domain.lectureTimeSlot.application;


import static org.mockito.Mockito.times;

import com.ggang.be.domain.constant.Gender;
import com.ggang.be.domain.constant.Mbti;
import com.ggang.be.domain.constant.WeekDate;
import com.ggang.be.domain.timslot.lectureTimeSlot.LectureTimeSlotEntity;
import com.ggang.be.domain.timslot.lectureTimeSlot.application.LectureTimeSlotServiceImpl;
import com.ggang.be.domain.timslot.lectureTimeSlot.vo.LectureTimeSlotVo;
import com.ggang.be.domain.timslot.lectureTimeSlot.infra.LectureTimeSlotRepository;
import com.ggang.be.domain.school.SchoolEntity;
import com.ggang.be.domain.user.UserEntity;
import java.util.List;
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
            .schoolGrade(3)
            .enterYear(2020)
            .mbti(Mbti.INFJ)
            .gender(Gender.MAN)
            .introduction("Hello, I am a test user.")
            .build();

        List<LectureTimeSlotVo> lectureTimeSlotVos = List.of(
            new LectureTimeSlotVo(WeekDate.MON, 9.0, 10.5),
            new LectureTimeSlotVo(WeekDate.WED, 14.0, 15.5),
            new LectureTimeSlotVo(WeekDate.FRI, 16.0, 17.0)
        );

        List<LectureTimeSlotEntity> list = lectureTimeSlotVos.stream()
            .map(lectureTimeSlotVo -> LectureTimeSlotVo.toEntity(lectureTimeSlotVo, testCaseUser))
            .toList();

        //when
        lectureTimeSlotServiceImpl.saveLectureTimeSlot(lectureTimeSlotVos, testCaseUser);

        //then

        Mockito.verify(lectureTimeSlotRepository, times(3)).save(ArgumentMatchers.any(LectureTimeSlotEntity.class));

    }
}