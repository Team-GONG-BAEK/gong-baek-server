package com.ggang.be.global.infra.oauth;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "apple")
public class AppleProperties {
    private String teamId;
    private String loginKey;
    private String clientId;
    private String redirectUri;
    private String keyPath;
}
