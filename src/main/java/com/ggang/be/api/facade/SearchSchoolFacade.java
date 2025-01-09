package com.ggang.be.api.facade;

import com.ggang.be.api.school.service.SchoolService;
import com.ggang.be.api.school.dto.SchoolSearchResponse;
import com.ggang.be.domain.school.dto.SchoolSearchVo;
import com.ggang.be.global.annotation.Facade;
import java.util.List;
import lombok.RequiredArgsConstructor;

@Facade
@RequiredArgsConstructor
public class SearchSchoolFacade {

    private final SchoolService schoolService;

    public SchoolSearchResponse searchSchool(final String searchKeyword) {
        List<SchoolSearchVo> schools = schoolService.searchSchoolContainingKeyword(searchKeyword);
        return SchoolSearchResponse.of(schools);
    }

}
