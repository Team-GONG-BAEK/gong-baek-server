package com.ggang.be.domain.group.onceGroup.infra;

import com.ggang.be.domain.constant.*;
import com.ggang.be.domain.gongbaekTImeSlot.GongbaekTimeSlotFixture;
import com.ggang.be.domain.group.everyGroup.EveryGroupEntity;
import com.ggang.be.domain.group.onceGroup.OnceGroupEntity;
import com.ggang.be.domain.school.SchoolEntity;
import com.ggang.be.domain.timslot.gongbaekTimeSlot.GongbaekTimeSlotEntity;
import com.ggang.be.domain.user.UserEntity;
import com.ggang.be.global.config.QuerydslConfigTest;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(QuerydslConfigTest.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@EntityScan(basePackages = "com.ggang.be.domain")
class OnceGroupRepositoryQuerydslTest {

    @Autowired
    private OnceGroupRepository onceGroupRepository;

    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("QueryDSL - 카테고리와 요일로 OnceGroup 조회")
    void findByCategoryAndWeekDay() {
        // given
        SchoolEntity schoolEntity = SchoolEntity.builder()
                .schoolName("school")
                .schoolDomain("school")
                .build();
        em.persist(schoolEntity);

        UserEntity userEntity = UserEntity.builder()
                .email("email")
                .platform(Platform.APPLE)
                .platformId("1")
                .nickname("nickname")
                .school(schoolEntity) // 저장된 school 사용
                .gender(Gender.MAN)
                .introduction("introduction")
                .mbti(Mbti.INFJ)
                .profileImg(1)
                .enterYear(2020)
                .schoolMajorName("Computer Science")
                .build();
        em.persist(userEntity);

        GongbaekTimeSlotEntity slotFriday = GongbaekTimeSlotFixture.createWithWeekDay(WeekDay.FRI);
        GongbaekTimeSlotEntity slotFriday2 = GongbaekTimeSlotFixture.createWithWeekDay(WeekDay.FRI);
        GongbaekTimeSlotEntity slotMonday = GongbaekTimeSlotFixture.createWithWeekDay(WeekDay.THU);
        em.persist(slotFriday);
        em.persist(slotFriday2);
        em.persist(slotMonday);

        // STUDY + FRIDAY (조회 대상)
        OnceGroupEntity group1 = OnceGroupEntity.builder()
                .userEntity(userEntity)
                .category(Category.STUDY)
                .status(Status.RECRUITING)
                .groupDate(LocalDate.of(2025, 12, 26))
                .gongbaekTimeSlotEntity(slotFriday)
                .title("스터디 금요일")
                .comments(new ArrayList<>())
                .coverImg(1)
                .location("Busan")
                .maxPeopleCount(10)
                .currentPeopleCount(3)
                .introduction("금요일 스터디")
                .build();
        em.persist(group1);

        // STUDY + MONDAY
        OnceGroupEntity group2 = OnceGroupEntity.builder()
                .userEntity(userEntity)
                .category(Category.STUDY)
                .status(Status.RECRUITING)
                .groupDate(LocalDate.of(2025, 12, 25))
                .gongbaekTimeSlotEntity(slotMonday)
                .title("스터디 월요일")
                .comments(new ArrayList<>())
                .coverImg(1)
                .location("Seoul")
                .maxPeopleCount(10)
                .currentPeopleCount(2)
                .introduction("월요일 스터디")
                .build();
        em.persist(group2);

        // DINING + FRIDAY
        OnceGroupEntity group3 = OnceGroupEntity.builder()
                .userEntity(userEntity)
                .category(Category.DINING)
                .status(Status.RECRUITING)
                .groupDate(LocalDate.of(2025, 12, 26))
                .gongbaekTimeSlotEntity(slotFriday2)
                .title("식사 모임")
                .comments(new ArrayList<>())
                .coverImg(1)
                .location("Daegu")
                .maxPeopleCount(8)
                .currentPeopleCount(5)
                .introduction("밥 먹자")
                .build();
        em.persist(group3);

        em.flush();
        em.clear();

        // when
        List<OnceGroupEntity> result1 = onceGroupRepository.findGroupsByCategoryAndDay(Category.STUDY, WeekDay.FRI);
        List<OnceGroupEntity> result2 = onceGroupRepository.findGroupsByCategoryAndDay(Category.STUDY, null);
        List<OnceGroupEntity> result3 = onceGroupRepository.findGroupsByCategoryAndDay(null, WeekDay.FRI);
        List<OnceGroupEntity> result4 = onceGroupRepository.findGroupsByCategoryAndDay(null, null);

        // then
        assertThat(result1).hasSize(1);
        assertThat(result2).hasSize(2);
        assertThat(result3).hasSize(2);
        assertThat(result4).hasSize(3);
        assertThat(result1.getFirst().getCategory()).isEqualTo(Category.STUDY);
        assertThat(result1.getFirst().getGongbaekTimeSlotEntity().getWeekDay()).isEqualTo(WeekDay.FRI);
        assertThat(result4.get(1).getGongbaekTimeSlotEntity().getWeekDay()).isEqualTo(WeekDay.THU);
    }
}
