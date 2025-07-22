package com.ggang.be.api.report.controller;


import com.ggang.be.api.common.ApiResponse;
import com.ggang.be.api.common.ResponseBuilder;
import com.ggang.be.api.common.ResponseSuccess;
import com.ggang.be.api.facade.ReportFacade;
import com.ggang.be.domain.constant.GroupType;
import com.ggang.be.global.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reports")
public class ReportController {

    private final ReportFacade reportFacade;
    private final JwtService jwtService;

    @PostMapping("/comment/{commentId}")
    public ResponseEntity<ApiResponse<ResponseSuccess>> reportComment(
            @RequestHeader("Authorization") final String token,
            @PathVariable final Long commentId
    ) {
        Long userId = jwtService.parseTokenAndGetUserId(token);
        return ResponseBuilder.created(reportFacade.reportComment(userId, commentId));
    }

    @PostMapping("/group/{groupId}")
    public ResponseEntity<ApiResponse<ResponseSuccess>> reportGroup(
            @RequestHeader("Authorization") final String token,
            @PathVariable final Long groupId,
            @RequestParam final GroupType groupType
    ) {
        Long userId = jwtService.parseTokenAndGetUserId(token);
        return ResponseBuilder.created(reportFacade.reportGroup(userId, groupId, groupType));
    }

}
