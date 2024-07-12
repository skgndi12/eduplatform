package com.weolbu.eduplatform.repository;

import com.weolbu.eduplatform.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByEmail(String email);
}
