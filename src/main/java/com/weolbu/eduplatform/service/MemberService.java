package com.weolbu.eduplatform.service;

import org.springframework.stereotype.Service;

import com.weolbu.eduplatform.entity.Member;
import com.weolbu.eduplatform.repository.MemberRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Member registerMember(Member member) {
        validatePassword(member.getPassword());
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        return memberRepository.save(member);
    }

    private void validatePassword(String password) {
        if (password.length() < 6 || password.length() > 10) {
            throw new IllegalArgumentException("Password must be between 6 and 10");
        }

        if (!password.matches("^(?=.*[a-z])(?=.*[A-Z0-9]).+$")) {
            throw new IllegalArgumentException(
                    "Password must contain at least one lowercase letter and one uppercase letter or number");
        }
    }

}
