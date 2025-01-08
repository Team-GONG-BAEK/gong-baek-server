package com.ggang.be.api.facade;

import com.ggang.be.api.school.controller.SchoolService;
import com.ggang.be.api.school.dto.SchoolSearchResponse;
import com.ggang.be.domain.school.dto.SchoolSearchVo;
import com.ggang.be.global.annotation.Facade;
import java.util.List;
import lombok.RequiredArgsConstructor;

@Facade
@RequiredArgsConstructor
public class SearchSchoolFacade {

    private final SchoolService schoolService;

    public SchoolSearchResponse searchSchool(final String schoolName) {
        List<SchoolSearchVo> schools = schoolService.searchSchoolContainingNames(schoolName);
        return SchoolSearchResponse.of(schools);
    }

}
