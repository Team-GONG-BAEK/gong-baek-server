package com.ggang.be.api.user.dto;

import com.ggang.be.domain.user.dto.UserProfile;

public record UserProfileResponse(
        String nickname,
        String schoolName,
        String majorName,
        int profileImg
) {
    public static UserProfileResponse of(UserProfile userProfile) {
        return new UserProfileResponse(
                userProfile.nickname(),
                userProfile.schoolName(),
                userProfile.schoolMajor(),
                userProfile.profileImg()
        );
    }
}
