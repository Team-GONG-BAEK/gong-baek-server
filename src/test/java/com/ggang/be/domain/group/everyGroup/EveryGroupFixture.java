package com.ggang.be.domain.group.everyGroup;

import com.ggang.be.domain.constant.Category;
import com.ggang.be.domain.constant.Status;
import com.ggang.be.domain.gongbaekTImeSlot.GongbaekTimeSlotFixture;
import com.ggang.be.domain.school.SchoolEntity;
import com.ggang.be.domain.user.UserEntity;
import com.ggang.be.domain.user.fixture.UserEntityFixture;

import java.time.LocalDate;
import java.util.ArrayList;

public class EveryGroupFixture {

    public static EveryGroupEntity getTestEveryGroup() {
        return EveryGroupEntity.builder()
                .userEntity(UserEntity.builder()
                        .nickname("GroupLeader")
                        .schoolMajorName("Software Engineering")
                        .profileImg(1)
                        .build()) // UserEntity 생성
                .comments(new ArrayList<>()) // 댓글 리스트 (비어있음)
                .dueDate(LocalDate.of(2024, 1, 1)) // 마감 날짜
                .gongbaekTimeSlotEntity(GongbaekTimeSlotFixture.getTestGongbaekTimeSlot()) // 공백 시간 슬롯
                .category(Category.DINING) // 열거형 값으로 가정
                .coverImg(2) // 커버 이미지
                .location("Busan") // 위치 정보
                .status(Status.CLOSED) // 열거형 값으로 가정
                .maxPeopleCount(15) // 최대 인원
                .currentPeopleCount(5) // 현재 참여 인원
                .introduction("This is a sample introduction for EveryGroupEntity.")
                .title("EveryGroup Weekly Meeting")
                .build();
    }

    public static EveryGroupEntity createBySchool(SchoolEntity school) {
        return EveryGroupEntity.builder()
                .userEntity(UserEntityFixture.createBySchool(school)) // UserEntity 생성
                .comments(new ArrayList<>()) // 댓글 리스트 (비어있음)
                .dueDate(LocalDate.of(2024, 1, 1)) // 마감 날짜
                .gongbaekTimeSlotEntity(GongbaekTimeSlotFixture.getTestGongbaekTimeSlot()) // 공백 시간 슬롯
                .category(Category.DINING) // 열거형 값으로 가정
                .coverImg(2) // 커버 이미지
                .location("Busan") // 위치 정보
                .status(Status.CLOSED) // 열거형 값으로 가정
                .maxPeopleCount(15) // 최대 인원
                .currentPeopleCount(5) // 현재 참여 인원
                .introduction("This is a sample introduction for EveryGroupEntity.")
                .title("EveryGroup Weekly Meeting")
                .build();
    }


    public static EveryGroupEntity createByUserNickname(String creatorByNickname) {
        return EveryGroupEntity.builder()
                .userEntity(UserEntity.builder()
                        .nickname(creatorByNickname)
                        .schoolMajorName("Software Engineering")
                        .profileImg(1)
                        .build()) // UserEntity 생성
                .comments(new ArrayList<>()) // 댓글 리스트 (비어있음)
                .dueDate(LocalDate.of(2024, 1, 1)) // 마감 날짜
                .gongbaekTimeSlotEntity(GongbaekTimeSlotFixture.getTestGongbaekTimeSlot()) // 공백 시간 슬롯
                .category(Category.DINING) // 열거형 값으로 가정
                .coverImg(2) // 커버 이미지
                .location("Busan") // 위치 정보
                .status(Status.CLOSED) // 열거형 값으로 가정
                .maxPeopleCount(15) // 최대 인원
                .currentPeopleCount(5) // 현재 참여 인원
                .introduction("This is a sample introduction for EveryGroupEntity.")
                .title("EveryGroup Weekly Meeting")
                .build();
    }

    public static EveryGroupEntity createByUser(UserEntity user) {
        return EveryGroupEntity.builder()
                .userEntity(user)
                .comments(new ArrayList<>())
                .dueDate(LocalDate.of(2024, 1, 1))
                .gongbaekTimeSlotEntity(GongbaekTimeSlotFixture.getTestGongbaekTimeSlot())
                .category(Category.DINING)
                .coverImg(2)
                .location("Busan")
                .status(Status.CLOSED)
                .maxPeopleCount(15)
                .currentPeopleCount(5)
                .introduction("This is a sample introduction for EveryGroupEntity.")
                .title("EveryGroup Weekly Meeting")
                .build();
    }
}
