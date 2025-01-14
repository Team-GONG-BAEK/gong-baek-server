package com.ggang.be.domain.comment.infra;

import com.ggang.be.domain.comment.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

}
