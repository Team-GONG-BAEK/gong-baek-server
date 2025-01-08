package com.ggang.be.api.school.controller;

import com.ggang.be.api.common.ApiResponse;
import com.ggang.be.api.common.ResponseSuccess;
import com.ggang.be.api.facade.SearchSchoolFacade;
import com.ggang.be.api.school.dto.SchoolSearchResponse;
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
public class SchoolController {

    private final SearchSchoolFacade searchSchoolFacade;


    @GetMapping("/school/search")
    public ResponseEntity<ApiResponse<SchoolSearchResponse>> searchSchool(@RequestParam final String schoolName) {
        log.info("schoolName: {}", schoolName);
        return ResponseEntity.ok(ApiResponse.success(ResponseSuccess.OK,
            searchSchoolFacade.searchSchool(schoolName)));
    }



}
