package com.ggang.be.api.email.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    private String reviewEmail;
    private String emailCode;
}
