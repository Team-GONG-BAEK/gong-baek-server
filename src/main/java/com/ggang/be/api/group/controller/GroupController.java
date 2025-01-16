package com.ggang.be.api.group.controller;

import com.ggang.be.api.common.ApiResponse;
import com.ggang.be.api.common.ResponseBuilder;
import com.ggang.be.api.common.ResponseError;
import com.ggang.be.api.common.ResponseSuccess;
import com.ggang.be.api.exception.GongBaekException;
import com.ggang.be.api.facade.GongbaekRequestFacade;
import com.ggang.be.api.facade.GroupFacade;
import com.ggang.be.api.group.dto.*;
import com.ggang.be.domain.constant.FillGroupType;
import com.ggang.be.domain.constant.GroupType;
import com.ggang.be.domain.group.dto.GroupVo;
import com.ggang.be.global.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class GroupController {
    private final GroupFacade groupFacade;
    private final GongbaekRequestFacade gongbaekRequestFacade;
    private final JwtService jwtService;

    @GetMapping("/fill/info")
    public ResponseEntity<ApiResponse<GroupResponse>> getGroupInfo(
            @RequestHeader("Authorization") final String accessToken,
            @RequestBody final GroupRequest groupRequestDto
    ) {
        Long userId = jwtService.parseTokenAndGetUserId(accessToken);

        GroupResponse groupResponseDto = groupFacade.getGroupInfo(
                groupRequestDto.groupType(), groupRequestDto.groupId(), userId
        );
        return ResponseEntity.ok(ApiResponse.success(ResponseSuccess.OK, groupResponseDto));
    }

    @GetMapping("/fill/user/info")
    public ResponseEntity<ApiResponse<GroupUserInfoResponseDto>> getGroupUserInfo(
            @RequestHeader("Authorization") final String accessToken,
            @RequestBody final GroupRequest groupRequestDto
    ){
        jwtService.isValidToken(accessToken);

        GroupUserInfoResponseDto groupUserInfoResponseDto
                = GroupUserInfoResponseDto.of(groupFacade.getGroupUserInfo(
                        groupRequestDto.groupType(), groupRequestDto.groupId()));
        return ResponseEntity.ok(ApiResponse.success(ResponseSuccess.OK, groupUserInfoResponseDto));
    }

    // TODO : 테스트 코드 작성 진행해야함!
    @PostMapping("/gongbaek")
    public ResponseEntity<ApiResponse<RegisterGongbaekResponse>> registerGongbaek(
        @RequestHeader("Authorization") String token,
        @RequestBody final RegisterGongbaekRequest dto
    ) {
        Long userId = jwtService.parseTokenAndGetUserId(token);

        gongbaekRequestFacade.validateRegisterRequest(userId, dto);

        return ResponseBuilder.created(groupFacade.registerGongbaek(userId, dto));
    }

    @GetMapping("/fill/members")
    public ResponseEntity<ApiResponse<ReadFillMembersResponse>> getGroupMembers(
        @RequestHeader("Authorization") String token, @RequestBody ReadFillMembersRequest dto) {
        Long userId = jwtService.parseTokenAndGetUserId(token);
        return ResponseBuilder.ok(groupFacade.getGroupUsersInfo(userId, dto));
    }

    @GetMapping("/my/groups")
    public ResponseEntity<ApiResponse<List<GroupVo>>> getMyGroups(
            @RequestHeader("Authorization") final String accessToken,
            @RequestBody final FillGroupFilterRequest groupFilterRequest
    ) {
        Long userId = jwtService.parseTokenAndGetUserId(accessToken);

        if (!FillGroupType.isValidCategory(groupFilterRequest.category())) {
            throw new GongBaekException(ResponseError.BAD_REQUEST);
        }

        return ResponseBuilder.ok(groupFacade.getMyGroups(userId, groupFilterRequest).groups());
    }

    @GetMapping("/fill/groups")
    public ResponseEntity<ApiResponse<List<ActiveGroupsResponse>>> getFillGroups(
            @RequestHeader("Authorization") final String accessToken
    ) {
        Long userId = jwtService.parseTokenAndGetUserId(accessToken);

        return ResponseBuilder.ok(groupFacade.getFillGroups(userId));
    }

    @GetMapping("/group/latest")
    public ResponseEntity<ApiResponse<List<ActiveGroupsResponse>>> getLatestGroups(
            @RequestHeader("Authorization") final String accessToken,
            @RequestParam("groupType") final GroupType groupType
    ) {
        Long userId = jwtService.parseTokenAndGetUserId(accessToken);

        return ResponseBuilder.ok(groupFacade.getLatestGroups(userId, groupType));
    }

    @GetMapping("/group/my/participation")
    public ResponseEntity<ApiResponse<NearestGroupResponse>> getNearestGroup(
            @RequestHeader("Authorization") final String accessToken
    ){
        Long userId = jwtService.parseTokenAndGetUserId(accessToken);

        return ResponseBuilder.ok(groupFacade.getNearestGroupInfo(userId));
    }

    @PostMapping("/group")
    public ResponseEntity<ApiResponse<Void>> applyGroup(
            @RequestHeader("Authorization") String accessToken,
            @RequestBody final GroupRequest requestDto
    ) {
        Long userId = jwtService.parseTokenAndGetUserId(accessToken);
        groupFacade.applyGroup(userId, requestDto);

        return ResponseBuilder.ok(null);
    }

    @PatchMapping("/my/groups")
    public ResponseEntity<ApiResponse<Void>> cancelMyApplication(
            @RequestHeader("Authorization") String accessToken,
            @RequestBody final GroupRequest requestDto
    ){
        Long userId = jwtService.parseTokenAndGetUserId(accessToken);
        groupFacade.cancelMyApplication(userId, requestDto);

        return ResponseBuilder.ok(null);
    }
}
