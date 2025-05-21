package com.ggang.be.api.email.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ConfigurationProperties(prefix = "app")
@Component
public class AppProperties {
    private String reviewEmail;
    private String emailCode;
}
