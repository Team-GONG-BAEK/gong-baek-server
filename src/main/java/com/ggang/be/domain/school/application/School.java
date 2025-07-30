package com.ggang.be.domain.school.application;

import com.ggang.be.domain.school.SchoolEntity;
import lombok.Builder;
import lombok.Getter;


@Getter
public class School {
    private final Long id;
    private final String schoolName;
    private final String schoolNameEn;
    private final String schoolDomain;

    public static School fromEntity(SchoolEntity schoolEntity) {
        return School.builder()
                .id(schoolEntity.getId())
                .schoolName(schoolEntity.getSchoolName())
                .schoolNameEn(schoolEntity.getSchoolNameEn())
                .schoolDomain(schoolEntity.getSchoolDomain())
                .build();
    }

    @Builder
    private School(Long id, String schoolName, String schoolNameEn, String schoolDomain) {
        this.id = id;
        this.schoolName = schoolName;
        this.schoolNameEn = schoolNameEn;
        this.schoolDomain = schoolDomain;
    }
}
