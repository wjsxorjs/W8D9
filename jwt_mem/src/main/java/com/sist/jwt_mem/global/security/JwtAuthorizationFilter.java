package com.sist.jwt_mem.global.security;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter{
    // JWT토큰을 가지고 서버에 들어오는 요청을 허용하기 위한
    // 인가(Authorization)처리를 하는 Filter 객체
    
    @Override
    @SneakyThrows // try~catch로 예외처리를 해야할 사항을 명시적으로 예외처리를 생략할 수 있도록 해준다.
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if( request.getRequestURI().equals("/api/member/login") || request.getRequestURI().equals("/api/member/logout")){
            filterChain.doFilter(request, response);
            return;
        } // login과 logout은 통과시킨다.

        // accessToken 검증 또는 refreshToken 발급 
        String accessToken = "";
        if(!accessToken.isBlank()){
            //accessToken 검증
            
        }
        filterChain.doFilter(request, response);

    }
    
}
