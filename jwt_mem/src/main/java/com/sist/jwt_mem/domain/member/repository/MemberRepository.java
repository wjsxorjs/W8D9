package com.sist.jwt_mem.domain.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.sist.jwt_mem.domain.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
    
    @Query("SELECT m FROM Member m WHERE m.mId = :mId")
    Optional<Member> findByMid(@Param("mId") String mId); //List의 데이터: 0~多개 | Optional의 데이터: 0~1개

    @Modifying // 여러 건을 처리할 때 연산을 빠르게 처리해준다. 안정적인 데이터 처리, 효율적인 실행. (단건 처리에는 X)
    @Transactional
    @Query("UPDATE Member m SET m.refreshToken = :refreshToken WHERE m.mId = :mId")
    void updateRefreshToken(@Param("mId") String mId, @Param("refreshToken") String refreshToken);
    

}