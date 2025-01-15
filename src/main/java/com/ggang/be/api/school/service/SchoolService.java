package com.ggang.be.api.school.service;

import com.ggang.be.domain.school.SchoolEntity;
import com.ggang.be.domain.school.application.School;
import com.ggang.be.domain.school.dto.SchoolSearchVo;

import java.util.List;

public interface SchoolService {
    List<SchoolSearchVo> searchSchoolContainingKeyword(String searchKeyword);

    School findSchoolByName(String schoolName);

    SchoolEntity findSchoolEntityByName(String s);
}
