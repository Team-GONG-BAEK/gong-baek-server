package com.ggang.be.domain.user.dto;

import com.ggang.be.domain.user.UserEntity;

public record UserInfo(
        int profileImg,
        String nickname,
        String sex,
        String schoolMajor,
        int enterYear,
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
                userEntity.getMbti().name(),
                userEntity.getIntroduction()
        );
    }
}