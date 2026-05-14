package com.operation.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpLogic;
import com.operation.service.SaTokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SaTokenConfig implements WebMvcConfigurer {

    @Bean
    public StpLogic getStpLogic() {
        return new StpLogic("login");
    }

    @Bean
    public StpInterface stpInterface(SaTokenService saTokenService) {
        return saTokenService;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SaInterceptor(handle -> {
                StpLogic stpLogic = new StpLogic("login");
                stpLogic.check();
            }))
            .addPathPatterns("/**")
            .excludePathPatterns(
                "/doc.html",
                "/swagger-ui.html",
                "/swagger-ui/**",
                "/swagger-resources/**",
                "/v3/api-docs/**",
                "/webjars/**",
                "/favicon.ico",
                "/error",
                "/auth/login",
                "/auth/captcha",
                "/uploads/**"
            )
            .order(1);
    }
}
