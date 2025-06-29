package com.ggang.be.domain.report.infra;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ggang.be.domain.constant.ReportType;
import com.ggang.be.domain.report.ReportEntity;

public interface ReportRepsitory extends JpaRepository <ReportEntity, Long>{

	List<ReportEntity> findByReportUserId(Long reportUserId);

	void deleteByTargetIdAndTargetType(Long targetId, ReportType targetType);
}
