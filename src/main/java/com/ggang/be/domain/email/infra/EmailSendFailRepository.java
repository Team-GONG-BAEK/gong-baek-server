package com.ggang.be.domain.email.infra;

import com.ggang.be.domain.email.EmailSendFailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailSendFailRepository extends JpaRepository<EmailSendFailEntity, Long> {
}
