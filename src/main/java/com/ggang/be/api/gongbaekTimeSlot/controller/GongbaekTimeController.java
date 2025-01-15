package com.ggang.be.api.gongbaekTimeSlot.controller;

import com.ggang.be.api.common.ApiResponse;
import com.ggang.be.api.common.ResponseBuilder;
import com.ggang.be.api.facade.TimeTableFacade;
import com.ggang.be.api.gongbaekTimeSlot.dto.ReadInvalidTimeResponse;
import com.ggang.be.global.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class GongbaekTimeController {
    private final JwtService jwtService;
    private final TimeTableFacade timeTableFacade;

    @GetMapping("/my/timeTable")
    public ResponseEntity<ApiResponse<ReadInvalidTimeResponse>> readMyInvalidTime(
        @RequestHeader("Authorization") final String accessToken
    ){
        Long userId = jwtService.parseTokenAndGetUserId(accessToken);

        return ResponseBuilder.ok(timeTableFacade.readMyInvalidTime(userId));
    }
}
