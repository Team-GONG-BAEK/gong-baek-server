package com.ggang.be.domain.schoolMajor.dto;

public record SearchSchoolMajorVo(String schoolMajor) {

    public static SearchSchoolMajorVo of(String schoolMajor) {
        return new SearchSchoolMajorVo(schoolMajor);
    }
}
