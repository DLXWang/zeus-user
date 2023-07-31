package com.xinxu.user.config;

import com.xinxu.user.filter.JwtCheckFilter;
import com.xinxu.user.service.IUserManageService;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
@ConfigurationProperties("security.jwt")
@Data
public class JwtConfig implements WebMvcConfigurer {
    private String private_key;
    private long access_token_expiration_time;
    private long refresh_token_expiration_time;
    private boolean enable_swagger;

    @Resource
    @Lazy
    IUserManageService userCenterApi;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(new JwtCheckFilter(userCenterApi));
        // add "/error", swagger 异常会请求/error
        if (enable_swagger) {
            interceptorRegistration.excludePathPatterns("/swagger-resources/**", "/v2/api-docs", "/error")
                    .order(Ordered.LOWEST_PRECEDENCE);
        }
    }
}
