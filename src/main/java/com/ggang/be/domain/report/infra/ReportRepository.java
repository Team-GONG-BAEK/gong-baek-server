package com.ggang.be.domain.report.infra;

import com.ggang.be.domain.constant.ReportType;
import com.ggang.be.domain.report.ReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<ReportEntity, Long> {

    List<ReportEntity> findByReportUserId(Long reportUserId);

    void deleteByTargetIdAndTargetType(Long targetId, ReportType targetType);

    void deleteAllByReportUserId(Long reportUserId);
}
