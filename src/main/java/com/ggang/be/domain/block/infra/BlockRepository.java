package com.ggang.be.domain.block.infra;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ggang.be.domain.block.BlockEntity;
import com.ggang.be.domain.report.ReportEntity;
import com.ggang.be.domain.user.UserEntity;

public interface BlockRepository extends JpaRepository<BlockEntity, Long> {

	@Query("select b.user from block b where b.id = :userId")
	List<UserEntity> findUserId(@Param("userId") Long userId);

	Optional<BlockEntity> findByReport(ReportEntity report);

	void deleteAllByUser(UserEntity user);
}
