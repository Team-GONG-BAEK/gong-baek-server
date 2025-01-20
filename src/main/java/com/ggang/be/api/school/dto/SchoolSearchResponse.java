package com.ggang.be.api.school.dto;

import java.util.List;

public record SchoolSearchResponse(List<String> schoolNames) {
    public static SchoolSearchResponse of(List<String> schoolNames) {
        return new SchoolSearchResponse(schoolNames);
    }
}
