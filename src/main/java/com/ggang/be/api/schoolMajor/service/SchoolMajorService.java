package com.ggang.be.api.schoolMajor.service;

import com.ggang.be.domain.schoolMajor.dto.SearchSchoolMajorVo;

import java.util.List;

public interface SchoolMajorService {
    List<SearchSchoolMajorVo> findSchoolMajorBySchoolAndMajorName(Long id, String schoolMajorKeyword);
}
