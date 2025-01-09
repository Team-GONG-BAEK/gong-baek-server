package com.ggang.be.api.group;

import com.ggang.be.api.common.ApiResponse;
import com.ggang.be.api.common.GroupResponse;
import com.ggang.be.api.common.ResponseError;
import com.ggang.be.api.common.ResponseSuccess;
import com.ggang.be.api.exception.GongBaekException;
import com.ggang.be.api.facade.GroupFacade;
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
        if (!GroupType.isValid(groupType)) {
            throw new GongBaekException(ResponseError.BAD_REQUEST);
        }

        GroupResponse groupResponse = groupFacade.getGroupInfo(groupType, groupId, accessToken);
        return ResponseEntity.ok(ApiResponse.success(ResponseSuccess.OK, groupResponse));
    }
}
