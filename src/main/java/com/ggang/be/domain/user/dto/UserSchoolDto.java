package com.ggang.be.domain.user.dto;

public record UserSchoolDto(
        String nickname,
        String schoolName
) {
    public static UserSchoolDto of(String nickname, String schoolName) {
        return new UserSchoolDto(nickname, schoolName);
    }
}
