package com.ggang.be.api.user.dto;

import com.ggang.be.domain.user.dto.UserSchoolDto;

public record UserSchoolResponseDto(String schoolName) {
    public static UserSchoolResponseDto of(UserSchoolDto userSchoolDto) {
        return new UserSchoolResponseDto(userSchoolDto.schoolName());
    }
}
