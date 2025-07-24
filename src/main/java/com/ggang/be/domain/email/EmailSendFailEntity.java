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

    public EmailSendFailEntity(String toEmail, String title, String errorMessage) {
        this.toEmail = toEmail;
        this.title = title;
        this.errorMessage = errorMessage;
    }
}