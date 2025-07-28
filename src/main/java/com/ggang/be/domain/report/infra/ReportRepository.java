package com.ggang.be.domain.report.infra;

import com.ggang.be.domain.constant.ReportType;
import com.ggang.be.domain.report.ReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReportRepository extends JpaRepository<ReportEntity, Long> {

    List<ReportEntity> findByReportUserId(Long reportUserId);

    void deleteByTargetIdAndTargetType(Long targetId, ReportType targetType);

    @Modifying
    @Query("delete from report r where r.reportUserId = :reportUserId")
    void deleteAllByReportUserId(@Param("reportUserId") Long reportUserId);

    @Modifying
    @Query("delete from report r where r.reportedUserId = :reportedUserId")
    void deleteAllByReportedUserId(@Param("reportedUserId") Long reportedUserId);
}
