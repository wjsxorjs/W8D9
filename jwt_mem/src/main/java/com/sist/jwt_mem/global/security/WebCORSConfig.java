package com.sist.jwt_mem.global.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebCORSConfig implements WebMvcConfigurer { // CORS 설정

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // localhost:3000으로부터 요청이 들어오는 때 모든 요청방식과
        // 모든 헤더에 실려오는 것을 허용함을 지정하자 
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:3000/")
                .allowedHeaders("*")
                .allowedMethods("*")
                .allowCredentials(true);
    }
    
    
}
