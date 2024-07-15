package com.weolbu.eduplatform.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.weolbu.eduplatform.dto.EnrollmentRequestDto;
import com.weolbu.eduplatform.dto.EnrollmentResponseDto;
import com.weolbu.eduplatform.entity.Enrollment;
import com.weolbu.eduplatform.entity.Lecture;
import com.weolbu.eduplatform.entity.Member;
import com.weolbu.eduplatform.exception.CustomException;
import com.weolbu.eduplatform.repository.EnrollmentRepository;
import com.weolbu.eduplatform.repository.LectureRepository;
import com.weolbu.eduplatform.repository.MemberRepository;

class EnrollmentServiceTest {

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private LectureRepository lectureRepository;

    @InjectMocks
    private EnrollmentService enrollmentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void enrollInLecture_Success() {
        EnrollmentRequestDto requestDto = new EnrollmentRequestDto();
        requestDto.setMemberId(1L);
        requestDto.setLectureId(1L);

        Member member = new Member();
        member.setId(1L);
        member.setName("John");

        Lecture lecture = new Lecture();
        lecture.setId(1L);
        lecture.setName("Java 101");
        lecture.setMaxStudents(10);

        Enrollment enrollment = new Enrollment();
        enrollment.setId(1L);
        enrollment.setMember(member);
        enrollment.setLecture(lecture);
        enrollment.setEnrollmentDate(LocalDateTime.now());

        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
        when(lectureRepository.findByIdWithLock(1L)).thenReturn(Optional.of(lecture));
        when(enrollmentRepository.countByLectureId(1L)).thenReturn(5L);
        when(enrollmentRepository.save(any(Enrollment.class))).thenReturn(enrollment);

        EnrollmentResponseDto responseDto = enrollmentService.enrollInLecture(requestDto);

        assertNotNull(responseDto);
        assertEquals(1L, responseDto.getMemberId());
        assertEquals(1L, responseDto.getLectureId());
        assertEquals("John", responseDto.getMemberName());
        assertEquals("Java 101", responseDto.getLectureName());

        verify(enrollmentRepository).save(any(Enrollment.class));
    }

    @Test
    void enrollInLecture_LectureFull() {
        EnrollmentRequestDto requestDto = new EnrollmentRequestDto();
        requestDto.setMemberId(1L);
        requestDto.setLectureId(1L);

        Member member = new Member();
        member.setId(1L);

        Lecture lecture = new Lecture();
        lecture.setId(1L);
        lecture.setMaxStudents(10);

        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
        when(lectureRepository.findByIdWithLock(1L)).thenReturn(Optional.of(lecture));
        when(enrollmentRepository.countByLectureId(1L)).thenReturn(10L);

        assertThrows(CustomException.class, () -> enrollmentService.enrollInLecture(requestDto));
    }
}
