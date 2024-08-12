package com.sist.jwt_mem.global.jwt;

import java.util.Base64;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component // 내가 만들기 싫어, Spring 너가 만들어줘!
public class JwtProvider{
    @Value("${custom.jwt.secretKey}")
    private String secretkKeyCode;

    private SecretKey secretKey;

    public SecretKey getSecretKey(){
        if(secretKey == null){
            String encoding = Base64.getEncoder()
                                    .encodeToString(secretkKeyCode.getBytes());
            
            secretKey = Keys.hmacShaKeyFor(encoding.getBytes());
        }

        return secretKey;
    }

    
    private String genToken(Map<String, Object> map, int seconds){
        long now = new Date().getTime();
        Date accessTokenExpiresIn = new Date(now+1000L*seconds);
        
        JwtBuilder jwtBuilder = Jwts.builder()
        .subject("jmg")
        .expiration(accessTokenExpiresIn);
        
        Set<String> keys = map.keySet();
        Iterator<String> it = keys.iterator();
        
        while(it.hasNext()){
            String key = it.next();
            Object value = map.get(key);
            jwtBuilder.claim(key, value); // 개인정보 저장
        }
        
        return jwtBuilder.signWith(getSecretKey())
        .compact(); // 개인정보가 저장된 builder로
        // Token 발행
    }
    
    public String getAccessToken(Map<String, Object> map){
        return genToken(map, 60*60); // 1시간
    }
    public String getRefreshToken(Map<String, Object> map){
        return genToken(map, 60*60*24*100); // 100일
    }
    // 토큰 유효기간 확인
    public boolean verify(String token){
        boolean value = true;

        try {
            Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token); //기간만료시 예외발생
        } catch (Exception e) {
            // 유효기간이 지났다는 뜻
            value = false;
        }

        return value;
    }


    // 토큰에 담긴 사용자정보(claims) 반환
    public Map<String, Object> getClaims(String token){
        return  Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
    }


}
