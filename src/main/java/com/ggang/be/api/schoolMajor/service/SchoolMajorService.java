package com.ggang.be.api.schoolMajor.service;

import java.util.List;

public interface SchoolMajorService {
    List<String> findSchoolMajorBySchoolAndMajorName(Long id, String schoolMajorKeyword);
}
