package com.weolbu.eduplatform.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.weolbu.eduplatform.dto.LectureRequestDto;
import com.weolbu.eduplatform.dto.LectureResponseDto;
import com.weolbu.eduplatform.entity.Lecture;
import com.weolbu.eduplatform.entity.Member;
import com.weolbu.eduplatform.exception.CustomException;
import com.weolbu.eduplatform.repository.LectureRepository;
import com.weolbu.eduplatform.repository.MemberRepository;

class LectureServiceTest {

    @Mock
    private LectureRepository lectureRepository;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private LectureService lectureService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createLecture_Success() {
        // Arrange
        LectureRequestDto requestDto = new LectureRequestDto();
        requestDto.setName("Java 101");
        requestDto.setMaxStudents(30);
        requestDto.setPrice(new BigDecimal("100.00"));
        requestDto.setInstructorId(1L);

        Member instructor = new Member();
        instructor.setId(1L);
        instructor.setName("John Doe");

        Lecture savedLecture = new Lecture();
        savedLecture.setId(1L);
        savedLecture.setName("Java 101");
        savedLecture.setMaxStudents(30);
        savedLecture.setPrice(new BigDecimal("100.00"));
        savedLecture.setInstructor(instructor);

        when(memberRepository.findById(1L)).thenReturn(Optional.of(instructor));
        when(lectureRepository.save(any(Lecture.class))).thenReturn(savedLecture);

        // Act
        LectureResponseDto responseDto = lectureService.createLecture(requestDto);

        // Assert
        assertNotNull(responseDto);
        assertEquals(1L, responseDto.getId());
        assertEquals("Java 101", responseDto.getName());
        assertEquals(30, responseDto.getMaxStudents());
        assertEquals(new BigDecimal("100.00"), responseDto.getPrice());
        assertEquals(1L, responseDto.getInstructorId());
        assertEquals("John Doe", responseDto.getInstructorName());

        verify(memberRepository).findById(1L);
        verify(lectureRepository).save(any(Lecture.class));
    }

    @Test
    void createLecture_InstructorNotFound() {
        // Arrange
        LectureRequestDto requestDto = new LectureRequestDto();
        requestDto.setInstructorId(1L);

        when(memberRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CustomException.class, () -> lectureService.createLecture(requestDto));
        verify(memberRepository).findById(1L);
        verify(lectureRepository, never()).save(any(Lecture.class));
    }

    @Test
    void getAllLectures_Success() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        Lecture lecture1 = new Lecture();
        lecture1.setId(1L);
        lecture1.setName("Java 101");
        lecture1.setInstructor(new Member());

        Lecture lecture2 = new Lecture();
        lecture2.setId(2L);
        lecture2.setName("Python 101");
        lecture2.setInstructor(new Member());

        Page<Lecture> lecturePage = new PageImpl<>(Arrays.asList(lecture1, lecture2));

        when(lectureRepository.findAll(pageable)).thenReturn(lecturePage);

        // Act
        Page<LectureResponseDto> result = lectureService.getAllLectures(pageable);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertEquals("Java 101", result.getContent().get(0).getName());
        assertEquals("Python 101", result.getContent().get(1).getName());

        verify(lectureRepository).findAll(pageable);
    }

    @Test
    void getLecturesByEnrollmentCount_Success() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        Lecture lecture1 = new Lecture();
        lecture1.setId(1L);
        lecture1.setName("Java 101");
        lecture1.setInstructor(new Member());

        Lecture lecture2 = new Lecture();
        lecture2.setId(2L);
        lecture2.setName("Python 101");
        lecture2.setInstructor(new Member());

        Page<Lecture> lecturePage = new PageImpl<>(Arrays.asList(lecture1, lecture2));

        when(lectureRepository.findAllOrderByEnrollmentCountDesc(pageable)).thenReturn(lecturePage);

        // Act
        Page<LectureResponseDto> result = lectureService.getLecturesByEnrollmentCount(pageable);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertEquals("Java 101", result.getContent().get(0).getName());
        assertEquals("Python 101", result.getContent().get(1).getName());

        verify(lectureRepository).findAllOrderByEnrollmentCountDesc(pageable);
    }

    @Test
    void getLecturesByEnrollmentRate_Success() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        Lecture lecture1 = new Lecture();
        lecture1.setId(1L);
        lecture1.setName("Java 101");
        lecture1.setInstructor(new Member());

        Lecture lecture2 = new Lecture();
        lecture2.setId(2L);
        lecture2.setName("Python 101");
        lecture2.setInstructor(new Member());

        Page<Lecture> lecturePage = new PageImpl<>(Arrays.asList(lecture1, lecture2));

        when(lectureRepository.findAllOrderByEnrollmentRateDesc(pageable)).thenReturn(lecturePage);

        // Act
        Page<LectureResponseDto> result = lectureService.getLecturesByEnrollmentRate(pageable);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertEquals("Java 101", result.getContent().get(0).getName());
        assertEquals("Python 101", result.getContent().get(1).getName());

        verify(lectureRepository).findAllOrderByEnrollmentRateDesc(pageable);
    }
}
