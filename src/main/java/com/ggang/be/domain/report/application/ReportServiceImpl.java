package com.ggang.be.domain.report.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ggang.be.api.report.service.ReportService;
import com.ggang.be.domain.constant.GroupType;
import com.ggang.be.domain.constant.ReportType;
import com.ggang.be.domain.report.ReportEntity;
import com.ggang.be.domain.report.infra.ReportRepsitory;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

	private final ReportRepsitory reportRepository;


	@Override
	@Transactional
	public ReportEntity reportComment(long commentId, long userId, long reportedId) {
		return reportRepository
			.save(buildReport(commentId, userId, reportedId, ReportType.COMMENT));
	}

	@Override
	@Transactional
	public void reportGroup(long groupId, long userId, long reportedId, GroupType groupType) {
		if(groupType == GroupType.ONCE)
			reportRepository
				.save(buildReport(groupId, userId, reportedId, ReportType.ONCE_GROUP));

		reportRepository.save(buildReport(groupId, userId, reportedId, ReportType.WEEKLY_GROUP));
	}

	@Override
	public List<ReportEntity> findReports(long userId) {
		return reportRepository.findByReportUserId(userId);
	}

	@Override
	@Transactional
	public void deleteReportByComment(long commentId) {
		reportRepository.deleteByTargetIdAndTargetType(commentId, ReportType.COMMENT);
	}

	@Override
	@Transactional
	public void deleteReportByGroup(long groupId, GroupType groupType) {
		if(groupType == GroupType.ONCE) {
			reportRepository.deleteByTargetIdAndTargetType(groupId, ReportType.ONCE_GROUP);
			return;
		}
		reportRepository.deleteByTargetIdAndTargetType(groupId, ReportType.WEEKLY_GROUP);
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
