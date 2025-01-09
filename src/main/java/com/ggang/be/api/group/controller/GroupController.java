package com.ggang.be.api.group.controller;

import com.ggang.be.api.common.ApiResponse;
import com.ggang.be.api.common.ResponseSuccess;
import com.ggang.be.api.facade.GroupFacade;
import com.ggang.be.api.group.dto.GroupResponse;
import com.ggang.be.domain.constant.GroupType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class GroupController {
    private final GroupFacade groupFacade;

    @GetMapping("/fill/info")
    public ResponseEntity<ApiResponse<GroupResponse>> getGroupInfo(
            @RequestHeader("Authorization") String accessToken,
            @RequestParam(value = "groupType") @NotBlank String groupType,
            @RequestParam(value = "groupId") @Min(value = 1, message = "groupId는 양수여야 합니다.") long groupId
    ) {
        GroupResponse groupResponse = groupFacade.getGroupInfo(GroupType.fromString(groupType.toUpperCase()), groupId, accessToken);
        return ResponseEntity.ok(ApiResponse.success(ResponseSuccess.OK, groupResponse));
    }
}
