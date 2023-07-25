package com.xinxu.user.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("security.jwt")
@Data
public class JwtConfig {
    private String private_key;
    private long access_token_expiration_time;
    private long refresh_token_expiration_time;
}
