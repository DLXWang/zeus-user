package com.xinxu.user.config;

import com.xinxu.user.util.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

public class SecurityConfig {

    @Bean
    public JwtUtil jwtUtil(JwtConfig jwtConfig) {
        return new JwtUtil(jwtConfig);
    }

}

