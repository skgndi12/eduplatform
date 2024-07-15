package com.weolbu.eduplatform.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weolbu.eduplatform.dto.LectureRequestDto;
import com.weolbu.eduplatform.dto.LectureResponseDto;
import com.weolbu.eduplatform.service.LectureService;

@WebMvcTest(LectureController.class)
@AutoConfigureMockMvc(addFilters = false)
class LectureControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private LectureService lectureService;

    @MockBean
    private PagedResourcesAssembler<LectureResponseDto> pagedResourcesAssembler;

    @Test
    void createLecture_Success() throws Exception {
        LectureRequestDto requestDto = new LectureRequestDto();
        requestDto.setName("Java 101");
        requestDto.setMaxStudents(10);
        requestDto.setPrice(new BigDecimal("100.00"));
        requestDto.setInstructorId(1L);

        LectureResponseDto responseDto = new LectureResponseDto();
        responseDto.setId(1L);
        responseDto.setName("Java 101");
        responseDto.setMaxStudents(10);
        responseDto.setPrice(new BigDecimal("100.00"));
        responseDto.setInstructorId(1L);
        responseDto.setInstructorName("John Doe");

        when(lectureService.createLecture(any(LectureRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(post("/api/v1/lectures")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Java 101"))
                .andExpect(jsonPath("$.maxStudents").value(10))
                .andExpect(jsonPath("$.price").value(100.00))
                .andExpect(jsonPath("$.instructorId").value(1))
                .andExpect(jsonPath("$.instructorName").value("John Doe"));
    }

    @Test
    void getAllLectures_Success() throws Exception {
        LectureResponseDto lecture1 = new LectureResponseDto();
        lecture1.setId(1L);
        lecture1.setName("Java 101");

        LectureResponseDto lecture2 = new LectureResponseDto();
        lecture2.setId(2L);
        lecture2.setName("Python 101");

        Page<LectureResponseDto> lecturePage = new PageImpl<>(Arrays.asList(lecture1, lecture2));

        when(lectureService.getAllLectures(any(Pageable.class))).thenReturn(lecturePage);
        when(pagedResourcesAssembler.toModel(any(Page.class))).thenReturn(PagedModel.empty());

        mockMvc.perform(get("/api/v1/lectures")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk());
    }

    @Test
    void getLecturesByEnrollmentCount_Success() throws Exception {
        LectureResponseDto lecture1 = new LectureResponseDto();
        lecture1.setId(1L);
        lecture1.setName("Java 101");

        LectureResponseDto lecture2 = new LectureResponseDto();
        lecture2.setId(2L);
        lecture2.setName("Python 101");

        Page<LectureResponseDto> lecturePage = new PageImpl<>(Arrays.asList(lecture1, lecture2));

        when(lectureService.getLecturesByEnrollmentCount(any(Pageable.class))).thenReturn(lecturePage);
        when(pagedResourcesAssembler.toModel(any(Page.class))).thenReturn(PagedModel.empty());

        mockMvc.perform(get("/api/v1/lectures/by-enrollment-count")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk());
    }

    @Test
    void getLecturesByEnrollmentRate_Success() throws Exception {
        LectureResponseDto lecture1 = new LectureResponseDto();
        lecture1.setId(1L);
        lecture1.setName("Java 101");

        LectureResponseDto lecture2 = new LectureResponseDto();
        lecture2.setId(2L);
        lecture2.setName("Python 101");

        Page<LectureResponseDto> lecturePage = new PageImpl<>(Arrays.asList(lecture1, lecture2));

        when(lectureService.getLecturesByEnrollmentRate(any(Pageable.class))).thenReturn(lecturePage);
        when(pagedResourcesAssembler.toModel(any(Page.class))).thenReturn(PagedModel.empty());

        mockMvc.perform(get("/api/v1/lectures/by-enrollment-rate")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk());
    }
}
