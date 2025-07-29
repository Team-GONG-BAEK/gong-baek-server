package com.ggang.be.domain.report.application;

import com.ggang.be.api.report.service.ReportService;
import com.ggang.be.domain.constant.GroupType;
import com.ggang.be.domain.constant.ReportType;
import com.ggang.be.domain.report.ReportEntity;
import com.ggang.be.domain.report.infra.ReportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ReportServiceImplTest {

    @Autowired
    private ReportService reportService;

    @Autowired
    private ReportRepository reportRepository;

    @BeforeEach
    void setUp() {
        reportRepository.deleteAll();
    }

    @Test
    @DisplayName("댓글 신고를 생성할 수 있다")
    @Transactional
    void reportComment() {
        // given
        long commentId = 1L;
        long userId = 100L;
        long reportedId = 200L;

        // when
        ReportEntity report = reportService.reportComment(commentId, userId, reportedId);

        // then
        assertThat(report.getId()).isNotNull();
        assertThat(report.getTargetId()).isEqualTo(commentId);
        assertThat(report.getTargetType()).isEqualTo(ReportType.COMMENT);
        assertThat(report.getReportUserId()).isEqualTo(userId);
        assertThat(report.getReportedUserId()).isEqualTo(reportedId);
    }

    @Test
    @DisplayName("일회성 모임 신고를 생성할 수 있다")
    @Transactional
    void reportOnceGroup() {
        // given
        long groupId = 1L;
        long userId = 100L;
        long reportedId = 200L;
        GroupType groupType = GroupType.ONCE;

        // when
        reportService.reportGroup(groupId, userId, reportedId, groupType);

        // then
        List<ReportEntity> reports = reportRepository.findAll();
        assertThat(reports).hasSize(2); // ONCE_GROUP과 WEEKLY_GROUP 둘 다 생성됨
        
        // ONCE_GROUP 신고 확인
        ReportEntity onceReport = reports.stream()
                .filter(r -> r.getTargetType() == ReportType.ONCE_GROUP)
                .findFirst()
                .orElseThrow();
        assertThat(onceReport.getTargetId()).isEqualTo(groupId);
        assertThat(onceReport.getReportUserId()).isEqualTo(userId);
        assertThat(onceReport.getReportedUserId()).isEqualTo(reportedId);
        
        // WEEKLY_GROUP 신고 확인
        ReportEntity weeklyReport = reports.stream()
                .filter(r -> r.getTargetType() == ReportType.WEEKLY_GROUP)
                .findFirst()
                .orElseThrow();
        assertThat(weeklyReport.getTargetId()).isEqualTo(groupId);
        assertThat(weeklyReport.getReportUserId()).isEqualTo(userId);
        assertThat(weeklyReport.getReportedUserId()).isEqualTo(reportedId);
    }

    @Test
    @DisplayName("다회성 그룹 신고를 생성할 수 있다")
    @Transactional
    void reportWeeklyGroup() {
        // given
        long groupId = 1L;
        long userId = 100L;
        long reportedId = 200L;
        GroupType groupType = GroupType.WEEKLY;

        // when
        reportService.reportGroup(groupId, userId, reportedId, groupType);

        // then
        List<ReportEntity> reports = reportRepository.findAll();
        assertThat(reports).hasSize(1); // WEEKLY_GROUP만 생성됨
        
        ReportEntity report = reports.get(0);
        assertThat(report.getTargetId()).isEqualTo(groupId);
        assertThat(report.getTargetType()).isEqualTo(ReportType.WEEKLY_GROUP);
        assertThat(report.getReportUserId()).isEqualTo(userId);
        assertThat(report.getReportedUserId()).isEqualTo(reportedId);
    }

    @Test
    @DisplayName("사용자가 신고한 모든 사용자 ID 목록을 조회할 수 있다")
    @Transactional
    void findReportedUserIds() {
        // given
        long userId = 100L;
        long reportedId1 = 200L;
        long reportedId2 = 300L;

        reportService.reportComment(1L, userId, reportedId1);
        reportService.reportComment(2L, userId, reportedId1); // 같은 사용자를 또 신고
        reportService.reportComment(3L, userId, reportedId2);

        // when
        List<Long> reportedUserIds = reportService.findReportedUserIds(userId);

        // then
        assertThat(reportedUserIds).hasSize(2);
        assertThat(reportedUserIds).containsExactlyInAnyOrder(reportedId1, reportedId2);
    }

    /**
     * 댓글이 삭제될 때 호출되는 메서드
     * 댓글과 관련된 모든 신고를 삭제한다
     */
    @Test
    @DisplayName("댓글 삭제 시 관련 신고도 함께 삭제된다")
    @Transactional
    void deleteReportByComment() {
        // given
        long commentId = 1L;
        long userId = 100L;
        long reportedId = 200L;

        reportService.reportComment(commentId, userId, reportedId);
        reportService.reportComment(2L, userId, reportedId); // 다른 댓글 신고

        // when
        reportService.deleteReportByComment(commentId);

        // then
        List<ReportEntity> reports = reportRepository.findAll();
        assertThat(reports).hasSize(1);
        assertThat(reports.get(0).getTargetId()).isEqualTo(2L);
    }

    /**
     * 모임이 삭제될 때 호출되는 메서드
     * 모임과 관련된 모든 신고를 삭제한다
     */
    @Test
    @DisplayName("일회성 모임 삭제 시 신고를 삭제할 수 있다")
    @Transactional
    void deleteReportByOnceGroup() {
        // given
        long groupId = 1L;
        long userId = 100L;
        long reportedId = 200L;

        reportService.reportGroup(groupId, userId, reportedId, GroupType.ONCE);
        reportService.reportGroup(2L, userId, reportedId, GroupType.ONCE); // 다른 그룹 신고

        // when
        reportService.deleteReportByGroup(groupId, GroupType.ONCE);

        // then
        List<ReportEntity> reports = reportRepository.findAll();
        assertThat(reports).hasSize(3); // 2L 그룹의 ONCE + WEEKLY 신고 2개 + groupId의 WEEKLY 신고 1개
        assertThat(reports).allMatch(report -> report.getTargetId() != groupId || report.getTargetType() != ReportType.ONCE_GROUP);
    }

    /**
     * 모임이 삭제될 때 호출되는 메서드
     * 모임과 관련된 모든 신고를 삭제한다
     */
    @Test
    @DisplayName("다회성 모임 삭제 시 신고를 삭제할 수 있다")
    @Transactional
    void deleteReportByWeeklyGroup() {
        // given
        long groupId = 1L;
        long userId = 100L;
        long reportedId = 200L;

        reportService.reportGroup(groupId, userId, reportedId, GroupType.WEEKLY);
        reportService.reportGroup(2L, userId, reportedId, GroupType.WEEKLY); // 다른 그룹 신고

        // when
        reportService.deleteReportByGroup(groupId, GroupType.WEEKLY);

        // then
        List<ReportEntity> reports = reportRepository.findAll();
        assertThat(reports).hasSize(1);
        assertThat(reports.get(0).getTargetId()).isEqualTo(2L);
    }

    /**
     * 탈퇴할 때 탈퇴하는 사용자와 관련된 모임을 삭제하는 메서드
     * 사용자와 신고한 모든 신고를 삭제한다
     */
    @Test
    @DisplayName("사용자가 신고한 모든 신고를 삭제할 수 있다")
    @Transactional
    void deleteAllReportsByUser() {
        // given
        long userId = 100L;
        long otherUserId = 200L;
        long reportedId = 300L;

        reportService.reportComment(1L, userId, reportedId);
        reportService.reportComment(2L, userId, reportedId);
        reportService.reportComment(3L, otherUserId, reportedId); // 다른 사용자의 신고

        // when
        reportService.deleteAllReportsByUser(userId);

        // then
        List<ReportEntity> reports = reportRepository.findAll();
        assertThat(reports).hasSize(1);
        assertThat(reports.get(0).getReportUserId()).isEqualTo(otherUserId);
    }

    /**
     * 탈퇴할 때 탈퇴하는 사용자와 관련된 모임을 삭제하는 메서드
     * 사용자가 신고당한 모든 신고를 삭제한다
     */
    @Test
    @DisplayName("신고당한 사용자에 대한 모든 신고를 삭제할 수 있다")
    @Transactional
    void deleteAllReportsByReportedUser() {
        // given
        long userId1 = 100L;
        long userId2 = 200L;
        long reportedId = 300L;

        reportService.reportComment(1L, userId1, reportedId);
        reportService.reportComment(2L, userId2, reportedId);
        reportService.reportComment(3L, userId1, 400L); // 다른 사용자 신고

        // when
        reportService.deleteAllReportsByReportedUser(reportedId);

        // then
        List<ReportEntity> reports = reportRepository.findAll();
        assertThat(reports).hasSize(1);
        assertThat(reports.get(0).getReportedUserId()).isEqualTo(400L);
    }

    @Test
    @DisplayName("여러 사용자가 같은 사용자를 신고해도 중복 제거된 목록을 반환한다")
    @Transactional
    void findReportedUserIdsWithDuplicates() {
        // given
        long userId1 = 100L;
        long userId2 = 200L;
        long reportedId = 300L;

        reportService.reportComment(1L, userId1, reportedId);
        reportService.reportComment(2L, userId1, reportedId); // 같은 사용자를 또 신고
        reportService.reportComment(3L, userId2, reportedId); // 다른 사용자가 같은 사용자 신고

        // when
        List<Long> reportedUserIds1 = reportService.findReportedUserIds(userId1);
        List<Long> reportedUserIds2 = reportService.findReportedUserIds(userId2);

        // then
        assertThat(reportedUserIds1).hasSize(1);
        assertThat(reportedUserIds1).containsExactly(reportedId);
        assertThat(reportedUserIds2).hasSize(1);
        assertThat(reportedUserIds2).containsExactly(reportedId);
    }
} 