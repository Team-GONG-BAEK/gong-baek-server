package com.ggang.be.api.school.service;

import com.ggang.be.domain.school.SchoolEntity;
import com.ggang.be.domain.school.application.School;

import java.util.List;

public interface SchoolService {
    List<String> searchSchoolContainingKeyword(String searchKeyword);

    School findSchoolByName(String schoolName);

    SchoolEntity findSchoolEntityByName(String schoolName);

    String findSchoolDomainByName(String schoolName);
}
