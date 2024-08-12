package com.sist.jwt_mem.domain.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true) //부모(Object)의 toString이 호출된다.
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="m_idx")
    private Long mIdx;

    @Column(name="m_id")
    private String mId;

    @Column(name="m_pw")
    // 비밀번호는 외부로 가는 것이 보안상 좋지 않기에
    // 다음과 같이 정의하여 JSON화에서 제외시킨다.
    @JsonIgnore
    private String mPw;
    
    @Column(name="m_name")
    private String mName;


    @Column(name="access_token", length = 1024)
    private String accessToken;
    
    @Column(name="refresh_token", length = 1024)
    private String refreshToken;
}