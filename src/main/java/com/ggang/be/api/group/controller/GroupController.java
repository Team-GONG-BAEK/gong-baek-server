package com.ggang.be.api.group.controller;

import com.ggang.be.api.common.ApiResponse;
import com.ggang.be.api.common.ResponseBuilder;
import com.ggang.be.api.common.ResponseError;
import com.ggang.be.api.common.ResponseSuccess;
import com.ggang.be.api.exception.GongBaekException;
import com.ggang.be.api.facade.GroupRequestFacade;
import com.ggang.be.api.group.dto.*;
import com.ggang.be.api.group.facade.GroupFacade;
import com.ggang.be.domain.constant.Category;
import com.ggang.be.domain.constant.FillGroupType;
import com.ggang.be.domain.constant.GroupType;
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
    private final GroupRequestFacade groupRequestFacade;
    private final JwtService jwtService;

    @GetMapping("/fill/info")
    public ResponseEntity<ApiResponse<GroupResponse>> getGroupInfo(
            @RequestHeader("Authorization") final String accessToken,
            @RequestParam("groupId") long groupId,
            @RequestParam("groupType") GroupType groupType
    ) {
        Long userId = jwtService.parseTokenAndGetUserId(accessToken);

        GroupResponse groupResponseDto = groupFacade.getGroupInfo(groupType, groupId, userId);
        return ResponseEntity.ok(ApiResponse.success(ResponseSuccess.OK, groupResponseDto));
    }

    @GetMapping("/fill/user/info")
    public ResponseEntity<ApiResponse<GroupUserInfoResponseDto>> getGroupUserInfo(
            @RequestHeader("Authorization") final String accessToken,
            @RequestParam("groupId") long groupId,
            @RequestParam("groupType") GroupType groupType
    ){
        jwtService.isValidToken(accessToken);

        GroupUserInfoResponseDto groupUserInfoResponseDto = GroupUserInfoResponseDto.of(
                groupFacade.getGroupUserInfo(groupType, groupId));

        return ResponseEntity.ok(ApiResponse.success(ResponseSuccess.OK, groupUserInfoResponseDto));
    }

    // TODO : 테스트 코드 작성 진행해야함!
    @PostMapping("/gongbaek")
    public ResponseEntity<ApiResponse<RegisterGroupResponse>> registerGongbaek(
            @RequestHeader("Authorization") String token,
            @RequestBody final RegisterGongbaekRequest dto
    ) {
        Long userId = jwtService.parseTokenAndGetUserId(token);
        groupRequestFacade.validateRegisterRequest(userId, dto);

        return ResponseBuilder.created(groupFacade.registerGroup(userId, dto));
    }

    @GetMapping("/fill/members")
    public ResponseEntity<ApiResponse<ReadFillMembersResponse>> getGroupMembers(
            @RequestHeader("Authorization") String token,
            @RequestParam("groupId") long groupId,
            @RequestParam("groupType") GroupType groupType
    ) {
        Long userId = jwtService.parseTokenAndGetUserId(token);
        ReadFillMembersRequest dto = new ReadFillMembersRequest(groupId, groupType);

        return ResponseBuilder.ok(groupFacade.getGroupUsersInfo(userId, dto));
    }

    @GetMapping("/my/groups")
    public ResponseEntity<ApiResponse<FinalMyGroupResponse>> getMyGroups(
            @RequestHeader("Authorization") final String accessToken,
            @RequestParam(value = "category") FillGroupType category,
            @RequestParam(value = "status") boolean status
    ) {
        Long userId = jwtService.parseTokenAndGetUserId(accessToken);

        if (!FillGroupType.isValidCategory(category)) {
            throw new GongBaekException(ResponseError.BAD_REQUEST);
        }

        FinalMyGroupResponse finalResponse = new FinalMyGroupResponse(
                groupFacade.getMyGroups(userId, category, status)
        );

        return ResponseBuilder.ok(finalResponse);
    }

    @GetMapping("/fill/groups")
    public ResponseEntity<ApiResponse<List<ActiveGroupsResponse>>> getFillGroups(
            @RequestHeader("Authorization") final String accessToken,
            @RequestParam(value = "category", required = false) Category category
    ) {
        Long userId = jwtService.parseTokenAndGetUserId(accessToken);

        return ResponseBuilder.ok(groupFacade.getFillGroups(userId, category));
    }

    @GetMapping("/group/latest")
    public ResponseEntity<ApiResponse<List<LatestResponse>>> getLatestGroups(
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

    @DeleteMapping("/my/groups")
    public ResponseEntity<ApiResponse<Void>> deleteMyGroup(
            @RequestHeader("Authorization") String accessToken,
            @RequestBody final GroupRequest requestDto
    ) {
        Long userId = jwtService.parseTokenAndGetUserId(accessToken);
        groupFacade.deleteMyGroup(userId, requestDto);

        return ResponseBuilder.ok(null);
    }
}
