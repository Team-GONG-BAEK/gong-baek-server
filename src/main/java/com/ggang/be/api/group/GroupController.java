package com.ggang.be.api.group;

import com.ggang.be.api.common.ApiResponse;
import com.ggang.be.api.common.GroupResponse;
import com.ggang.be.api.common.ResponseSuccess;
import com.ggang.be.api.facade.GroupFacade;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class GroupController {
    private final GroupFacade groupFacade;

    public GroupController(GroupFacade groupFacade) {
        this.groupFacade = groupFacade;
    }

    @GetMapping("/fill/info")
    public ResponseEntity<ApiResponse<GroupResponse>> getGroupInfo(
            @RequestHeader("Authorization") String accessToken,
            @RequestParam(value = "groupType") String groupType,
            @RequestParam(value = "groupId") @Min(value = 1, message = "groupId는 양수여야 합니다.") long groupId
    ) {
        GroupResponse groupResponse = groupFacade.getGroupInfo(groupType, groupId);
        return ResponseEntity.ok(ApiResponse.success(ResponseSuccess.OK, groupResponse));
    }
}
