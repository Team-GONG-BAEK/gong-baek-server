package com.ggang.be.domain.report.application;

import com.ggang.be.api.report.service.ReportService;
import com.ggang.be.domain.constant.GroupType;
import com.ggang.be.domain.constant.ReportType;
import com.ggang.be.domain.report.ReportEntity;
import com.ggang.be.domain.report.infra.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;

    @Override
    @Transactional
    public ReportEntity reportComment(long commentId, long userId, long reportedId) {
        return reportRepository
                .save(buildReport(commentId, userId, reportedId, ReportType.COMMENT));
    }

    @Override
    @Transactional
    public void reportGroup(long groupId, long userId, long reportedId, GroupType groupType) {
        if (groupType == GroupType.ONCE)
            reportRepository
                    .save(buildReport(groupId, userId, reportedId, ReportType.ONCE_GROUP));

        reportRepository.save(buildReport(groupId, userId, reportedId, ReportType.WEEKLY_GROUP));
    }

    @Override
    public List<ReportEntity> findReports(long userId) {
        return reportRepository.findByReportUserId(userId);
    }

    @Override
    public List<Long> findReportedUserIds(long reportUserId) {
        return reportRepository.findByReportUserId(reportUserId).stream()
                .map(ReportEntity::getReportedUserId)
                .distinct()
                .toList();
    }

    @Override
    @Transactional
    public void deleteReportByComment(long commentId) {
        reportRepository.deleteByTargetIdAndTargetType(commentId, ReportType.COMMENT);
    }

    @Override
    @Transactional
    public void deleteReportByGroup(long groupId, GroupType groupType) {
        if (groupType == GroupType.ONCE) {
            reportRepository.deleteByTargetIdAndTargetType(groupId, ReportType.ONCE_GROUP);
            return;
        }
        reportRepository.deleteByTargetIdAndTargetType(groupId, ReportType.WEEKLY_GROUP);
    }

    @Override
    @Transactional
    public void deleteAllReportsByUser(Long userId) {
        reportRepository.deleteAllByReportUserId(userId);
    }

    @Override
    @Transactional
    public void deleteAllReportsByReportedUser(Long userId) {
        reportRepository.deleteAllByReportedUserId(userId);
    }

    private ReportEntity buildReport(long targetId, long userId, long reportedId, ReportType groupType) {
        return ReportEntity.builder()
                .targetId(targetId)
                .targetType(groupType)
                .reportUserId(userId)
                .reportedUserId(reportedId)
                .build();
    }
}
