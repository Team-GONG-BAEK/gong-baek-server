package com.ggang.be.api.schoolMajor.service;

import java.util.List;

public interface SchoolMajorService {
    List<String> findSchoolMajorBySchoolAndMajorName(Long id, String schoolMajorKeyword);
    
    List<String> findSchoolMajorBySchoolAndMajorNameBoth(Long id, String schoolMajorKeyword);
}
