package com.ggang.be.api.report.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ggang.be.api.common.ApiResponse;
import com.ggang.be.api.common.ResponseBuilder;
import com.ggang.be.api.common.ResponseSuccess;
import com.ggang.be.api.facade.ReportFacade;
import com.ggang.be.domain.constant.GroupType;
import com.ggang.be.global.jwt.JwtService;

import lombok.RequiredArgsConstructor;

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
		){
		Long userId = jwtService.parseTokenAndGetUserId(token);
		reportFacade.reportComment(userId, commentId);
		return ResponseBuilder.created(ResponseSuccess.CREATED);

	}


	@PostMapping("/group/{groupId}")
	public ResponseEntity<ApiResponse<ResponseSuccess>> reportGroup(
		@RequestHeader("Authorization") final String token,
		@PathVariable final Long groupId,
		@RequestParam final GroupType groupType
	){
		Long userId = jwtService.parseTokenAndGetUserId(token);
		reportFacade.reportGroup(userId, groupId, groupType);
		return ResponseBuilder.created(ResponseSuccess.CREATED);
	}

}
