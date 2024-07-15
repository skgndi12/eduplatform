package com.weolbu.eduplatform.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.weolbu.eduplatform.dto.MemberRequestDto;
import com.weolbu.eduplatform.dto.MemberResponseDto;
import com.weolbu.eduplatform.entity.Member;
import com.weolbu.eduplatform.entity.MemberType;
import com.weolbu.eduplatform.exception.CustomException;
import com.weolbu.eduplatform.repository.MemberRepository;

class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private MemberService memberService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerMember_Success() {
        MemberRequestDto requestDto = new MemberRequestDto();
        requestDto.setName("John");
        requestDto.setEmail("john@example.com");
        requestDto.setPassword("password");
        requestDto.setPhoneNumber("010-1234-5678");
        requestDto.setMemberType(MemberType.STUDENT);

        Member member = new Member(1L, "John", "john@example.com", "encodedPassword", "010-1234-5678",
                MemberType.STUDENT);

        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(memberRepository.save(any(Member.class))).thenReturn(member);

        MemberResponseDto responseDto = memberService.registerMember(requestDto);

        assertNotNull(responseDto);
        assertEquals("John", responseDto.getName());
        assertEquals("john@example.com", responseDto.getEmail());

        verify(passwordEncoder).encode("password");
        verify(memberRepository).save(any(Member.class));
    }

    @Test
    void registerMember_DuplicateEmail() {
        MemberRequestDto requestDto = new MemberRequestDto();
        requestDto.setEmail("john@example.com");

        when(memberRepository.findByEmail("john@example.com")).thenReturn(new Member());

        assertThrows(CustomException.class, () -> memberService.registerMember(requestDto));
    }
}
