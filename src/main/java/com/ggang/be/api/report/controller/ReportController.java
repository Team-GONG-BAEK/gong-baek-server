package com.ggang.be.api.report.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ggang.be.api.facade.ReportFacade;
import com.ggang.be.api.report.dto.ReportCommentResponse;
import com.ggang.be.api.report.dto.ReportGroupResponse;
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
	public ResponseEntity<ReportCommentResponse> reportComment(
		@RequestHeader("Authorization") final String token,
		@PathVariable final Long commentId
		){
		Long userId = jwtService.parseTokenAndGetUserId(token);
		return ResponseEntity.ok(reportFacade.reportComment(userId, commentId));
	}


	@PostMapping("/group/{groupId}")
	public ResponseEntity<ReportGroupResponse> reportGroup(
		@RequestHeader("Authorization") final String token,
		@PathVariable final Long groupId,
		@RequestParam final GroupType groupType
	){
		Long userId = jwtService.parseTokenAndGetUserId(token);
		return ResponseEntity.ok(reportFacade.reportGroup(userId, groupId, groupType));
	}

}
