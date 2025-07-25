package com.ggang.be.api.email.service;

import com.ggang.be.api.common.ResponseError;
import com.ggang.be.api.exception.GongBaekException;
import com.ggang.be.domain.email.EmailSendFailEntity;
import com.ggang.be.domain.email.infra.EmailSendFailRepository;
import io.sentry.Sentry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender emailSender;
    private final EmailSendFailRepository emailSendFailRepository;

    public String createCode() {
        try {
            Random random = SecureRandom.getInstanceStrong();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < 6; i++) {
                builder.append(random.nextInt(10));
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            log.debug("MemberService.createCode() exception occur");
            throw new GongBaekException(ResponseError.INTERNAL_SERVER_ERROR);
        }
    }

    @Async("taskExecutor")
    public void sendEmail(
            String toEmail,
            String title,
            String text
    ) {
        SimpleMailMessage emailForm = createEmailForm(toEmail, title, text);
        try {
            emailSender.send(emailForm);
        } catch (Exception e) {
            String sentryMessage = String.format(
                    "[메일 전송 실패] 이메일 계정=%s, 이메일 제목=%s, 에러 메세지=%s",
                    toEmail,
                    title,
                    e.getMessage()
            );
            Sentry.captureException(new RuntimeException(sentryMessage, e));

            log.error(sentryMessage);
            emailSendFailRepository.save(
                    EmailSendFailEntity.of(toEmail, title, e.getMessage())
            );
        }
    }

    private SimpleMailMessage createEmailForm(
            String toEmail,
            String title,
            String text
    ) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(title);
        message.setText(text);

        return message;
    }
}
