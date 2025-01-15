package com.ggang.be.domain.school.fixture;

import com.ggang.be.domain.school.SchoolEntity;

public class SchoolEntityFixture {

    public static SchoolEntity createSchoolName(String schoolName) {
        return SchoolEntity.builder()
            .schoolName(schoolName)
            .build();
    }

}
