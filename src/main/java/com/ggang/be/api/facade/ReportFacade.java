package com.ggang.be.api.facade;

import com.ggang.be.api.comment.service.CommentService;
import com.ggang.be.api.common.ResponseSuccess;
import com.ggang.be.api.group.dto.GroupCreatorVo;
import com.ggang.be.api.group.dto.GroupRequest;
import com.ggang.be.api.group.facade.GroupFacade;
import com.ggang.be.api.report.service.ReportService;
import com.ggang.be.domain.block.application.BlockServiceImpl;
import com.ggang.be.domain.comment.CommentEntity;
import com.ggang.be.domain.constant.GroupType;
import com.ggang.be.domain.report.ReportEntity;
import com.ggang.be.domain.user.UserEntity;
import com.ggang.be.global.annotation.Facade;
import lombok.RequiredArgsConstructor;

@Facade
@RequiredArgsConstructor
public class ReportFacade {

    private final ReportService reportService;
    private final BlockServiceImpl blockService;
    private final GroupFacade groupFacade;
    private final CommentService commentService;

    public ResponseSuccess reportComment(long reporterId, long commentId) {
        CommentEntity commentEntity = commentService.findById(commentId);
        UserEntity reportedUser = commentEntity.getUserEntity();

        ReportEntity reportEntity = reportService.reportComment(
                commentId, reporterId, reportedUser.getId());

        blockService.blockUser(reportEntity, reportedUser);

        groupFacade.cancelApplicationToReportedUserGroups(reporterId, reportedUser.getId());

        return ResponseSuccess.CREATED;
    }


    public ResponseSuccess reportGroup(long userId, long groupId, GroupType groupType) {
        GroupCreatorVo groupCreator = groupFacade.findGroupCreator(groupType, groupId);
        GroupRequest request = GroupRequest.of(groupId, groupType);

        groupFacade.cancelMyApplication(userId, request);
        reportService.reportGroup(groupId, userId, groupCreator.creatorId(), groupType);

        return ResponseSuccess.CREATED;
    }

}
