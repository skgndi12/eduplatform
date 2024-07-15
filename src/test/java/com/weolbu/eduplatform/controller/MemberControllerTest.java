package com.weolbu.eduplatform.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weolbu.eduplatform.dto.MemberRequestDto;
import com.weolbu.eduplatform.dto.MemberResponseDto;
import com.weolbu.eduplatform.entity.MemberType;
import com.weolbu.eduplatform.service.MemberService;

@WebMvcTest(MemberController.class)
@AutoConfigureMockMvc(addFilters = false)
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MemberService memberService;

    @Test
    void registerMember_Success() throws Exception {
        MemberRequestDto requestDto = new MemberRequestDto();
        requestDto.setName("John");
        requestDto.setEmail("john@example.com");
        requestDto.setPassword("password");
        requestDto.setPhoneNumber("010-1234-5678");
        requestDto.setMemberType(MemberType.STUDENT);

        MemberResponseDto responseDto = new MemberResponseDto();
        responseDto.setId(1L);
        responseDto.setName("John");
        responseDto.setEmail("john@example.com");
        responseDto.setPhoneNumber("010-1234-5678");
        responseDto.setMemberType(MemberType.STUDENT);

        when(memberService.registerMember(any(MemberRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(post("/api/v1/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.email").value("john@example.com"))
                .andExpect(jsonPath("$.phoneNumber").value("010-1234-5678"))
                .andExpect(jsonPath("$.memberType").value("STUDENT"));

        verify(memberService).registerMember(any(MemberRequestDto.class));
    }
}
