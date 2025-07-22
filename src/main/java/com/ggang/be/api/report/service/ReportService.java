package com.ggang.be.api.report.service;

import com.ggang.be.domain.constant.GroupType;
import com.ggang.be.domain.report.ReportEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ReportService {
    ReportEntity reportComment(long commentId, long reportId, long reportedId);

    void reportGroup(long groupId, long reportId, long reportedId, GroupType groupType);

    List<ReportEntity> findReports(long userId);

    void deleteReportByComment(long commentId);

    void deleteReportByGroup(long groupId, GroupType groupType);

    @Transactional
    void deleteAllReportsByUser(Long userId);
}
