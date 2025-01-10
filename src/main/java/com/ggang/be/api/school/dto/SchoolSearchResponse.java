package com.ggang.be.api.school.dto;

import com.ggang.be.domain.school.dto.SchoolSearchVo;
import java.util.List;

public record SchoolSearchResponse(List<SchoolSearchVo> schoolNames) {
    public static SchoolSearchResponse of(List<SchoolSearchVo> schoolNames) {
        return new SchoolSearchResponse(schoolNames);
    }
}
