package com.vlad.exchangerate.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "external.provider.exchange-rates-api")
@Data
public class ExternalApiConfig {
    private String url;
    private String apiKey;
    private String scheduledFixedRate;
}
