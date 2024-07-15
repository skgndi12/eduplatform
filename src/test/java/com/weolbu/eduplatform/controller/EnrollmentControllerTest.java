package com.weolbu.eduplatform.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weolbu.eduplatform.dto.EnrollmentRequestDto;
import com.weolbu.eduplatform.dto.EnrollmentResponseDto;
import com.weolbu.eduplatform.service.EnrollmentService;

@WebMvcTest(EnrollmentController.class)
@AutoConfigureMockMvc(addFilters = false)
class EnrollmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EnrollmentService enrollmentService;

    @Test
    void enrollInLecture_Success() throws Exception {
        EnrollmentRequestDto requestDto = new EnrollmentRequestDto();
        requestDto.setMemberId(1L);
        requestDto.setLectureId(1L);

        EnrollmentResponseDto responseDto = new EnrollmentResponseDto();
        responseDto.setId(1L);
        responseDto.setMemberId(1L);
        responseDto.setLectureId(1L);
        responseDto.setMemberName("John Doe");
        responseDto.setLectureName("Java 101");
        responseDto.setEnrollmentDate(LocalDateTime.now());

        when(enrollmentService.enrollInLecture(any(EnrollmentRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(post("/api/v1/enrollments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.memberId").value(1))
                .andExpect(jsonPath("$.lectureId").value(1))
                .andExpect(jsonPath("$.memberName").value("John Doe"))
                .andExpect(jsonPath("$.lectureName").value("Java 101"))
                .andExpect(jsonPath("$.enrollmentDate").exists());

        verify(enrollmentService).enrollInLecture(any(EnrollmentRequestDto.class));
    }
}
