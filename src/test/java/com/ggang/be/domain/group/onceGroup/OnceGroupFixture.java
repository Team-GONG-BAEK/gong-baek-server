package com.ggang.be.domain.group.onceGroup;

import com.ggang.be.domain.constant.Category;
import com.ggang.be.domain.constant.Status;
import com.ggang.be.domain.gongbaekTImeSlot.GongbaekTimeSlotFixture;
import com.ggang.be.domain.school.SchoolEntity;
import com.ggang.be.domain.user.UserEntity;

import java.time.LocalDate;
import java.util.ArrayList;

public class OnceGroupFixture {

    public static OnceGroupEntity getTestOnceGroupEntity() {
        return OnceGroupEntity.builder()
                .title("Sample Group Title")
                .introduction("This is a sample group introduction.")
                .currentPeopleCount(5)
                .maxPeopleCount(10)
                .status(Status.CLOSED) // Enum 값으로 가정
                .location("Seoul")
                .coverImg(1)
                .category(Category.DINING) // Enum 값으로 가정
                .gongbaekTimeSlotEntity(GongbaekTimeSlotFixture.getTestGongbaekTimeSlot()) // 공백 시간 슬롯
                .groupDate(LocalDate.of(2023, 12, 25)) // 2023년 12월 25일
                .comments(new ArrayList<>()) // 비어있는 댓글 리스트
                .userEntity(UserEntity.builder()
                        .nickname("TestUser")
                        .schoolMajorName("Computer Science")
                        .profileImg(1)
                        .build()) // UserEntity 데이터
                .build();
    }

    public static OnceGroupEntity createByUserNickname(String nickname) {
        return OnceGroupEntity.builder()
                .title("Sample Group Title")
                .introduction("This is a sample group introduction.")
                .currentPeopleCount(5)
                .maxPeopleCount(10)
                .status(Status.CLOSED) // Enum 값으로 가정
                .location("Seoul")
                .coverImg(1)
                .category(Category.DINING) // Enum 값으로 가정
                .gongbaekTimeSlotEntity(GongbaekTimeSlotFixture.getTestGongbaekTimeSlot()) // 공백 시간 슬롯
                .groupDate(LocalDate.of(2023, 12, 25)) // 2023년 12월 25일
                .comments(new ArrayList<>()) // 비어있는 댓글 리스트
                .userEntity(UserEntity.builder()
                        .nickname(nickname)
                        .schoolMajorName("Computer Science")
                        .profileImg(1)
                        .build()) // UserEntity 데이터
                .build();
    }

    public static OnceGroupEntity createBySchool(SchoolEntity schoolEntity) {
        return OnceGroupEntity.builder()
                .title("Sample Group Title")
                .introduction("This is a sample group introduction.")
                .currentPeopleCount(5)
                .maxPeopleCount(10)
                .status(Status.CLOSED) // Enum 값으로 가정
                .location("Seoul")
                .coverImg(1)
                .category(Category.DINING) // Enum 값으로 가정
                .gongbaekTimeSlotEntity(GongbaekTimeSlotFixture.getTestGongbaekTimeSlot()) // 공백 시간 슬롯
                .groupDate(LocalDate.of(2023, 12, 25)) // 2023년 12월 25일
                .comments(new ArrayList<>()) // 비어있는 댓글 리스트
                .userEntity(UserEntity.builder()
                        .nickname("test")
                        .school(schoolEntity)
                        .schoolMajorName("Computer Science")
                        .profileImg(1)
                        .build()) // UserEntity 데이터
                .build();
    }

    public static OnceGroupEntity createByUser(UserEntity user) {
        return OnceGroupEntity.builder()
                .userEntity(user)
                .comments(new ArrayList<>())
                .gongbaekTimeSlotEntity(GongbaekTimeSlotFixture.getTestGongbaekTimeSlot())
                .category(Category.DINING)
                .coverImg(2)
                .location("Busan")
                .status(Status.CLOSED)
                .maxPeopleCount(15)
                .currentPeopleCount(5)
                .introduction("This is a sample introduction for OnceGroupEntity.")
                .title("OnceGroup Weekly Meeting")
                .build();
    }
}
