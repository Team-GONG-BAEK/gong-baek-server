package com.ggang.be.api.schoolMajor.dto;

import java.util.List;

public record SearchedSchoolMajorResponse(List<String> schoolMajors) {
    public static SearchedSchoolMajorResponse of(List<String> schoolMajors) {
        return new SearchedSchoolMajorResponse(schoolMajors);
    }
}
