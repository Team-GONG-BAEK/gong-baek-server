package com.ggang.be.domain.block.infra;

import com.ggang.be.domain.block.BlockEntity;
import com.ggang.be.domain.report.ReportEntity;
import com.ggang.be.domain.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BlockRepository extends JpaRepository<BlockEntity, Long> {

    @Query("select b.user from block b where b.id = :userId")
    List<UserEntity> findUserId(@Param("userId") Long userId);

    Optional<BlockEntity> findByReport(ReportEntity report);

    @Modifying
    @Query("delete from block b where b.user = :user")
    void deleteAllByUser(@Param("user") UserEntity user);

    @Modifying
    @Query("delete from block b where b.report.reportedUserId = :userId")
    void deleteAllByBlockedUserId(@Param("userId") Long userId);

    @Modifying
    @Query("delete from block b where b.report = :report")
    void deleteByReport(@Param("report") ReportEntity report);
}
