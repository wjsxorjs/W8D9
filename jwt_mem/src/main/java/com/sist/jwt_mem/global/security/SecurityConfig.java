package com.sist.jwt_mem.global.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    // 무조건 이 객체가 들어와야한다. 하지만 @Autowired를 쓰기에는
    // SecurityConfig가 먼저 움직여 해당 값이 들어오지않을 가능성이 크기에 적합한 방법이 아니다.
    // 해당 변수를 상수로 선언하고 @RequiredArgsConstructor로 정해주면 해당 뜻은 간단히 말해
    // 필요한 변수를 받아야만 만들어지도록 하는 생성자로. 자동대입이 가능해진다.
    private final JwtAuthorizationFilter jwtAuthorizationFilter;
    
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
        .requestMatchers("/api/member/**").permitAll()
        .requestMatchers(HttpMethod.POST,"/api/member/login").permitAll()
        .requestMatchers(
            new AntPathRequestMatcher("/**")).permitAll()) //사용자가 요정한 요청 정보를 확인하여 url을 확인 후 허용 
            .csrf(csrf -> {
                csrf.ignoringRequestMatchers("/api/member/**");
                csrf.ignoringRequestMatchers("/h2-console/**");
            })
            .httpBasic(
                httpBasic -> httpBasic.disable()
            ) // httpBasic 로그인 방식 끄기
            .formLogin(
                formLogin -> formLogin.disable()
            ) // formLogin 방식 끄기
            .sessionManagement(
                sessionManagement -> sessionManagement.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS
                )
            ) // session 끄기
            .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
            .headers(headers -> headers
            .addHeaderWriter(new XFrameOptionsHeaderWriter(
                XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN
            )));
        return http.build();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}