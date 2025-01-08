package com.ggang.be.api.school.controller;

import com.ggang.be.domain.school.dto.SchoolSearchVo;
import java.util.List;

public interface SchoolService {

    List<SchoolSearchVo> searchSchoolContainingKeyword(String searchKeyword);
}
