package com.ggang.be.api.group.dto;

import com.ggang.be.domain.user.dto.UserInfo;

public record GroupUserInfoResponseDto(
        int profileImg,
        String nickname,
        String sex,
        String schoolMajor,
        int enterYear,
        String mbti,
        String introduction
) {
    public static GroupUserInfoResponseDto of(UserInfo userInfo){
        return new GroupUserInfoResponseDto(
                userInfo.profileImg(),
                userInfo.nickname(),
                userInfo.sex(),
                userInfo.schoolMajor(),
                userInfo.enterYear(),
                userInfo.mbti(),
                userInfo.introduction()
        );
    }
}

