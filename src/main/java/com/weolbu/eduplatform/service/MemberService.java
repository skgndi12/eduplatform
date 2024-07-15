package com.weolbu.eduplatform.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.weolbu.eduplatform.dto.MemberRequestDto;
import com.weolbu.eduplatform.dto.MemberResponseDto;
import com.weolbu.eduplatform.entity.Member;
import com.weolbu.eduplatform.exception.CustomException;
import com.weolbu.eduplatform.repository.MemberRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public MemberResponseDto registerMember(MemberRequestDto request) {
        if (memberRepository.findByEmail(request.getEmail()) != null) {
            throw new CustomException("Email already exists");
        }

        Member member = Member.builder().name(request.getName()).email(request.getEmail())
                .phoneNumber(request.getPhoneNumber()).password(passwordEncoder.encode(request.getPassword()))
                .meberType(request.getMemberType()).build();

        Member savedMember = memberRepository.save(member);
        return convertToResponseDto(savedMember);
    }

    private MemberResponseDto convertToResponseDto(Member member) {
        MemberResponseDto response = new MemberResponseDto();
        response.setId(member.getId());
        response.setName(member.getName());
        response.setEmail(member.getEmail());
        response.setPhoneNumber(member.getPhoneNumber());
        response.setMemberType(member.getMeberType());
        return response;
    }
}
