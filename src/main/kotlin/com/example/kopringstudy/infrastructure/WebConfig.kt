package com.example.kopringstudy.infrastructure

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig(val loggingInterceptor: LoggingInterceptor) : WebMvcConfigurer {
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(loggingInterceptor)
            .addPathPatterns("/**")  // 모든 경로에 대해 인터셉터 적용
            .excludePathPatterns("/static/**", "/error") // 특정 경로는 제외 가능
    }
}
