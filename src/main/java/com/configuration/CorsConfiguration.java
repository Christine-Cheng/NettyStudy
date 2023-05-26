package com.configuration;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Describe: 后端解决CORS策略拒绝问题
 * 添加一个映射,允许配置的类型进行访问
 * @Author Happy
 * @Create 2023/4/12-23:25
 **/
public class CorsConfiguration implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS")
                .allowCredentials(true)//是否允许cookie
                .maxAge(3600)//设置有效期
                .allowedHeaders("*");
    }
}
