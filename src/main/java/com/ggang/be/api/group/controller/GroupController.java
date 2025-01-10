package com.ggang.be.api.group.controller;

import com.ggang.be.api.common.ApiResponse;
import com.ggang.be.api.common.ResponseSuccess;
import com.ggang.be.api.facade.GroupFacade;
import com.ggang.be.api.facade.GroupType;
import com.ggang.be.api.group.dto.GroupRequestDto;
import com.ggang.be.api.group.dto.GroupResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class GroupController {
    private final GroupFacade groupFacade;

    @GetMapping("/fill/info")
    public ResponseEntity<ApiResponse<GroupResponseDto>> getGroupInfo(
            @RequestHeader("Authorization") String accessToken,
            @RequestBody final GroupRequestDto groupRequestDto
            ) {
        GroupResponseDto groupResponseDto = groupFacade.getGroupInfo(
                GroupType.fromString(groupRequestDto.groupType()), groupRequestDto.groupId(), accessToken
        );
        return ResponseEntity.ok(ApiResponse.success(ResponseSuccess.OK, groupResponseDto));
    }
}
