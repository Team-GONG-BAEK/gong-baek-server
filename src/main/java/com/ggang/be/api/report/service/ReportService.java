package com.ggang.be.api.report.service;

import java.util.List;

import com.ggang.be.domain.constant.GroupType;
import com.ggang.be.domain.report.ReportEntity;

public interface ReportService {
	ReportEntity reportComment(long commentId, long reportId, long reportedId);
	void reportGroup(long groupId, long reportId, long reportedId, GroupType groupType);

	List<ReportEntity> findReports(long userId);

	void deleteReportByComment(long commentId);

	void deleteReportByGroup(long groupId, GroupType groupType);
}
