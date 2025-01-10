package com.ggang.be.api.schoolMajor.dto;

import com.ggang.be.domain.schoolMajor.dto.SearchSchoolMajorVo;
import java.util.List;

public record SearchedSchoolMajorResponse(List<SearchSchoolMajorVo> schoolMajors) {
    public static SearchedSchoolMajorResponse of(List<SearchSchoolMajorVo> schoolMajors) {
        return new SearchedSchoolMajorResponse(schoolMajors);
    }
}
