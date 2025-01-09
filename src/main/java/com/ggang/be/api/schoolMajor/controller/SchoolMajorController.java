package com.ggang.be.api.schoolMajor.controller;

import com.ggang.be.api.common.ApiResponse;
import com.ggang.be.api.common.ResponseBuilder;
import com.ggang.be.api.facade.SearchSchoolMajorFacade;
import com.ggang.be.api.schoolMajor.dto.SearchedSchoolMajorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class SchoolMajorController {

    private final SearchSchoolMajorFacade searchSchoolMajorFacade;

    @GetMapping("/school/major/search")
    public ResponseEntity<ApiResponse<SearchedSchoolMajorResponse>> searchSchoolMajorBySchoolName(
        @RequestParam("schoolName") String schoolName,
        @RequestParam("majorName") String schoolMajorKeyword) {

        log.info("schoolName : {}", schoolName);
        log.info("schoolMajorKeyword : {}", schoolMajorKeyword);

        return ResponseBuilder.ok(
            searchSchoolMajorFacade.searchSchoolMajorBySchoolName(schoolName, schoolMajorKeyword));
    }


}
