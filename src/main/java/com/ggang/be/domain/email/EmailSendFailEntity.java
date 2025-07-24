package com.ggang.be.domain.email;

import com.ggang.be.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmailSendFailEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String toEmail;
    private String title;
    private String errorMessage;

    @Builder
    private EmailSendFailEntity(String toEmail, String title, String errorMessage) {
        this.toEmail = toEmail;
        this.title = title;
        this.errorMessage = errorMessage;
    }

    public static EmailSendFailEntity of(String toEmail, String title, String errorMessage) {
        return new EmailSendFailEntity(toEmail, title, errorMessage);
    }
}