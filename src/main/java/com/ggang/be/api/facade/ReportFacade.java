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

    public ResponseSuccess reportComment(long userId, long commentId) {

        CommentEntity commentEntity = commentService.findById(commentId);

        UserEntity userEntity = commentEntity.getUserEntity();
        long reportedId = userEntity.getId();

        ReportEntity reportEntity = reportService.reportComment(commentId, userId, reportedId);

        blockService.blockUser(reportEntity, userEntity);

        return ResponseSuccess.CREATED;
    }


    public ResponseSuccess reportGroup(long userId, long groupId, GroupType groupType) {

        GroupCreatorVo groupCreator = groupFacade.findGroupCreator(groupType, groupId);
        groupFacade.cancelMyApplication(userId, new GroupRequest(groupId, groupType));
        reportService.reportGroup(groupId, userId, groupCreator.creatorId(), groupType);

        return ResponseSuccess.CREATED;
    }

}
