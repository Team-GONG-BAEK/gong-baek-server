package com.ggang.be.domain.user.dto;

import com.ggang.be.domain.user.UserEntity;

public record UserInfo(
        int profileImg,
        String nickname,
        String sex,
        String schoolMajor,
        int enterYear,
        int schoolGrade,
        String mbti,
        String introduction
) {
    public static UserInfo of(UserEntity userEntity) {
        return new UserInfo(
                userEntity.getProfileImg(),
                userEntity.getNickname(),
                userEntity.getGender().name(),
                userEntity.getSchoolMajorName(),
                userEntity.getEnterYear(),
                userEntity.getSchoolGrade(),
                userEntity.getMbti().name(),
                userEntity.getIntroduction()
        );
    }
}