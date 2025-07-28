package com.ggang.be.domain.report;

import com.ggang.be.domain.BaseTimeEntity;
import com.ggang.be.domain.constant.ReportType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "report")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ReportEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long targetId;

    @Enumerated(EnumType.STRING)
    private ReportType targetType;

    private Long reportUserId;

    private Long reportedUserId;

    @Builder
    public ReportEntity(Long targetId, ReportType targetType, Long reportUserId, Long reportedUserId) {
        this.targetId = targetId;
        this.targetType = targetType;
        this.reportUserId = reportUserId;
        this.reportedUserId = reportedUserId;
    }

}
