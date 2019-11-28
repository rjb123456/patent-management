package com.sxf.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author ：huang_qh@suixingpay.com
 * @date ：Created in 2019/11/25 14:16
 * @description：拦截配置
 */

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(userInterceptor()).addPathPatterns("/**")
//                .excludePathPatterns("/login")
//                .excludePathPatterns("/register");
//    }
//
//    @Bean
//    public WebInterceptor userInterceptor() {
//        return new WebInterceptor();
//    }
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedHeaders("*")
                .allowedOrigins("*")
                .allowedMethods("*");
    }
}
