package com.ggang.be.domain.schoolMajor.application;

import com.ggang.be.domain.school.application.School;
import com.ggang.be.domain.schoolMajor.SchoolMajorEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SchoolMajor {
    private Long id;
    private School school;
    private String majorName;

    public static SchoolMajor fromEntity(SchoolMajorEntity schoolMajorEntity){
        return SchoolMajor.builder()
            .id(schoolMajorEntity.getId())
            .school(School.fromEntity(schoolMajorEntity.getSchool()))
            .majorName(schoolMajorEntity.getMajorName())
            .build();
    }

    @Builder
    private SchoolMajor(Long id, School school, String majorName) {
        this.id = id;
        this.school = school;
        this.majorName = majorName;
    }
}
