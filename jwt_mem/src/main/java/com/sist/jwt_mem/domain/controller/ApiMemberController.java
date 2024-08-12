package com.sist.jwt_mem.domain.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sist.jwt_mem.domain.member.entity.Member;
import com.sist.jwt_mem.domain.member.service.MemberService;
import com.sist.jwt_mem.global.jwt.JwtProvider;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/api/member")
public class ApiMemberController {
    
    @Autowired
    private MemberService m_service;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;
    

    @PostMapping("/login")
    @ResponseBody
    public Map<String, Object> login(Member mvo, HttpServletResponse response) {
        Map<String, Object> m_map = new HashMap<>();
        
        // 파라미터로 전달된 member의 mId값을 가지고 검색

        if(mvo.getMId() != null){
            
            Member member = m_service.memInfo(mvo.getMId(), mvo.getMPw()); // 로그인 정보가 불일치하다면 반환값인 member은 null이 된다.

            if(member != null){
                // 세션 처리 시 중복 로그인이 가능하다. 그리고 세션을 따로 할당받아서 동시작업이 가능하기에
                // 이를 방지하기 위해 중복로그인이 안되고 동시작업도 불가한 토큰 처리한다.
                // 그리고 여러 서버를 사용할 시 세션 처리는 서버 간 통신이 불가하여 한 서버에서 세션을 할당 받았을 경우
                // 다른 서버에서 제공하는 서비스를 이용하지 못하거나 기본 서버와 다른 서버에 할당받는 상황이 생기기에 이를 해결하기 위해서도 토큰 처리를 해준다.
                ResponseCookie cookie = ResponseCookie.from("accessToken",member.getAccessToken())
                                                      .path("/")
                                                      .sameSite("none") // 같은 사이트에서도 허용하나? 아니
                                                      .httpOnly(true)   // http 사용 시에만
                                                      .secure(true)
                                                      .build();
                // 만든 쿠키를 클라이언트에게 줘야한다.
                response.addHeader("Set-Cookie", cookie.toString());
                
                cookie =  ResponseCookie.from("refreshToken",member.getRefreshToken())
                                        .path("/")
                                        .sameSite("none") // 같은 사이트에서도 허용하나? 아니
                                        .httpOnly(true)   // http 사용 시에만
                                        .secure(true)
                                        .build();

                response.addHeader("Set-Cookie", cookie.toString());

            }
            
            m_map.put("mvo", member);
        }
        return m_map;
    }

    @PostMapping("/reg")
    @ResponseBody
    public Map<String, Object> reg(Member mvo) {
        Map<String, Object> m_map = new HashMap<>();

        if(mvo.getMId() != null){
            Member result = m_service.regMember(mvo);

            m_map.put("result", result);
        }
        return m_map;
    }
    


}
