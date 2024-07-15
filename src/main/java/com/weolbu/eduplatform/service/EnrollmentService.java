package com.weolbu.eduplatform.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.weolbu.eduplatform.dto.EnrollmentRequestDto;
import com.weolbu.eduplatform.dto.EnrollmentResponseDto;
import com.weolbu.eduplatform.entity.Enrollment;
import com.weolbu.eduplatform.entity.Lecture;
import com.weolbu.eduplatform.entity.Member;
import com.weolbu.eduplatform.exception.CustomException;
import com.weolbu.eduplatform.repository.EnrollmentRepository;
import com.weolbu.eduplatform.repository.LectureRepository;
import com.weolbu.eduplatform.repository.MemberRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final MemberRepository memberRepository;
    private final LectureRepository lectureRepository;

    @Transactional
    public EnrollmentResponseDto enrollInLecture(EnrollmentRequestDto request) {
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new EntityNotFoundException("Member not found"));
        Lecture lecture = lectureRepository.findById(request.getLectureId())
                .orElseThrow(() -> new EntityNotFoundException("Lecture not found"));

        synchronized (this) {
            long curEnrollments = enrollmentRepository.countByLectureId(request.getLectureId());
            if (curEnrollments >= lecture.getMaxStudents()) {
                throw new CustomException("Lecture is already full");
            }

            Enrollment enrollment = Enrollment.builder().member(member).lecture(lecture)
                    .enrollmentDate(LocalDateTime.now()).build();

            Enrollment savedEnrollment = enrollmentRepository.save(enrollment);
            return convertToResponseDto(savedEnrollment);
        }
    }

    private EnrollmentResponseDto convertToResponseDto(Enrollment enrollment) {
        EnrollmentResponseDto response = new EnrollmentResponseDto();
        response.setId(enrollment.getId());
        response.setMemberId(enrollment.getMember().getId());
        response.setMemberName(enrollment.getMember().getName());
        response.setLectureId(enrollment.getLecture().getId());
        response.setLectureName(enrollment.getLecture().getName());
        response.setEnrollmentDate(enrollment.getEnrollmentDate());
        return response;
    }
}
