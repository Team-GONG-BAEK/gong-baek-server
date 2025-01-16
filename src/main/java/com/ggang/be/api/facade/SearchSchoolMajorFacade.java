package com.ggang.be.api.facade;

import com.ggang.be.api.school.service.SchoolService;
import com.ggang.be.api.schoolMajor.dto.SearchedSchoolMajorResponse;
import com.ggang.be.api.schoolMajor.service.SchoolMajorService;
import com.ggang.be.domain.school.application.School;
import com.ggang.be.domain.schoolMajor.dto.SearchSchoolMajorVo;
import com.ggang.be.global.annotation.Facade;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Facade
@RequiredArgsConstructor
public class SearchSchoolMajorFacade {
    private final SchoolMajorService schoolMajorService;
    private final SchoolService schoolService;

    public SearchedSchoolMajorResponse searchSchoolMajorBySchoolName(String schoolName, String schoolMajorKeyword) {
        School schoolByName = schoolService.findSchoolByName(schoolName);
        List<SearchSchoolMajorVo> findSearchedSchoolMajor = schoolMajorService.findSchoolMajorBySchoolAndMajorName(
            schoolByName.getId(), schoolMajorKeyword);

        return SearchedSchoolMajorResponse.of(findSearchedSchoolMajor);
    }
}
