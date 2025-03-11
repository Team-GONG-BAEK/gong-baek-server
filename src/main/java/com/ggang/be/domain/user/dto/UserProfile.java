package com.ggang.be.domain.user.dto;

import com.ggang.be.domain.user.UserEntity;

public record UserProfile(
        String nickname,
        String schoolName,
        String schoolMajor,
        int profileImg
) {
    public static UserProfile of(UserEntity userEntity) {
        return new UserProfile(
                userEntity.getNickname(),
                userEntity.getSchool().getSchoolName(),
                userEntity.getSchoolMajorName(),
                userEntity.getProfileImg()
        );
    }
}