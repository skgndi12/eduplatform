package com.weolbu.eduplatform.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.weolbu.eduplatform.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByEmail(String email);
}
