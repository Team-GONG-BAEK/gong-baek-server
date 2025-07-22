package com.ggang.be.api.user.facade;

import com.ggang.be.api.comment.service.CommentService;
import com.ggang.be.api.gongbaekTimeSlot.service.GongbaekTimeSlotService;
import com.ggang.be.api.group.everyGroup.service.EveryGroupService;
import com.ggang.be.api.group.onceGroup.service.OnceGroupService;
import com.ggang.be.api.lectureTimeSlot.service.LectureTimeSlotService;
import com.ggang.be.api.user.service.UserService;
import com.ggang.be.api.userEveryGroup.service.UserEveryGroupService;
import com.ggang.be.api.userOnceGroup.service.UserOnceGroupService;
import com.ggang.be.domain.block.application.BlockServiceImpl;
import com.ggang.be.domain.report.application.ReportServiceImpl;
import com.ggang.be.domain.user.UserEntity;
import com.ggang.be.global.annotation.Facade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Facade
@RequiredArgsConstructor
@Slf4j
public class UserFacade {

    private final UserService userService;
    private final CommentService commentService;
    private final LectureTimeSlotService lectureTimeSlotService;
    private final EveryGroupService everyGroupService;
    private final OnceGroupService onceGroupService;
    private final GongbaekTimeSlotService gongbaekTimeSlotService;
    private final UserOnceGroupService userOnceGroupService;
    private final UserEveryGroupService userEveryGroupService;
    private final BlockServiceImpl blockService;
    private final ReportServiceImpl reportService;

    @Transactional
    public void deleteUser(Long userId) {
        UserEntity user = userService.getUserById(userId);
        log.info("Fetched user: {}", user);

        log.info("== Deleting lecture time slots");
        lectureTimeSlotService.deleteUserTime(user);

        log.info("== Canceling true false applied groups");
        deleteUserGroupRelations(user);

        log.info("== Removing comment author");
        removeCommentAuthor(userId);

        log.info("== Removing group host");
        modifyGroupStatus(user);
        removeGroupHost(user);

        log.info("== Removing gongbaek time slot user");
        removeGongbaekTimeSlotUser(userId);

        log.info("== Removing blocks associated with user");
        deleteBlocksByUser(user);

        log.info("== Deleting Reports associated with user");
        deleteReportsByUser(userId);

        log.info("== Deleting user from repository");
        userService.deleteUser(userId);
    }

    private void deleteReportsByUser(Long userId) {
        reportService.deleteAllReportsByUser(userId);
    }

    private void deleteBlocksByUser(UserEntity user) {
        blockService.deleteBlocksByUser(user);
    }

    private void removeCommentAuthor(long userId) {
        log.info("removeCommentAuthor called with userId={}", userId);
        commentService.removeCommentAuthor(userId);
    }

    private void removeGroupHost(UserEntity user) {
        log.info("Removing group host for user: {}", user.getId());
        everyGroupService.deleteGroupHost(user);
        onceGroupService.deleteGroupHost(user);
    }

    private void modifyGroupStatus(UserEntity user) {
        everyGroupService.modifyGroupStatus(user);
        onceGroupService.modifyGroupStatus(user);
    }

    private void removeGongbaekTimeSlotUser(long userId) {
        gongbaekTimeSlotService.removeGongbaekTimeSlotUser(userId);
    }

    private void deleteUserGroupRelations(UserEntity user) {
        userEveryGroupService.deleteUserEveryGroup(user);
        userOnceGroupService.deleteUserOnceGroup(user);
    }
}
