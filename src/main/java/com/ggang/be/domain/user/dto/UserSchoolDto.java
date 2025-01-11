package com.ggang.be.domain.user.dto;

public record UserSchoolDto(String schoolName) {
    public static UserSchoolDto of(String schoolName) {
        return new UserSchoolDto(schoolName);
    }
}
