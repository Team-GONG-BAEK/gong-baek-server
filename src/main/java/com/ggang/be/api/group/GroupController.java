package com.ggang.be.api.group;

import com.ggang.be.api.common.ApiResponse;
import com.ggang.be.api.common.GroupResponse;
import com.ggang.be.api.common.ResponseSuccess;
import com.ggang.be.api.facade.GroupFacade;
import com.ggang.be.domain.user.UserEntity;
import com.ggang.be.domain.user.UserService;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class GroupController {
    private final GroupFacade groupFacade;
    private final UserService userService;

    public GroupController(GroupFacade groupFacade, UserService userService) {
        this.groupFacade = groupFacade;
        this.userService = userService;
    }

    @GetMapping("/fill/info")
    public ResponseEntity<ApiResponse<GroupResponse>> getGroupInfo(
            @RequestHeader("Authorization") String accessToken,
            @RequestParam(value = "groupType") String groupType,
            @RequestParam(value = "groupId") @Min(value = 1, message = "groupId는 양수여야 합니다.") long groupId
    ) {

        // 일단은 authorization을 UserId로 사용할 수 있게 했어요!
        UserEntity userInfo = userService.getUserById(Long.parseLong(accessToken));

        GroupResponse groupResponse = groupFacade.getGroupInfo(groupType, groupId, userInfo);
        return ResponseEntity.ok(ApiResponse.success(ResponseSuccess.OK, groupResponse));
    }
}
