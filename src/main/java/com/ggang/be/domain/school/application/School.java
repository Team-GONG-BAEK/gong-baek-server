package com.ggang.be.domain.school.application;

import com.ggang.be.domain.school.SchoolEntity;
import lombok.Builder;
import lombok.Getter;


@Getter
public class School {

    private Integer id;
    private String schoolName;
    private String schoolDomain;

    public static School fromEntity(SchoolEntity schoolEntity){
        return School.builder()
            .id(schoolEntity.getId())
            .schoolName(schoolEntity.getSchoolName())
            .schoolDomain(schoolEntity.getSchoolDomain())
            .build();
    }

    @Builder
    private School(Integer id, String schoolName, String schoolDomain) {
        this.id = id;
        this.schoolName = schoolName;
        this.schoolDomain = schoolDomain;
    }
}
