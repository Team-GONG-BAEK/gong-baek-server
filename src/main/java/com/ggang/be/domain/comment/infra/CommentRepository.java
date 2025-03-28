package com.ggang.be.domain.comment.infra;

import com.ggang.be.domain.comment.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findAllByUserEntity_Id(Long userId);
}
