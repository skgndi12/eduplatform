package com.weolbu.eduplatform.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.weolbu.eduplatform.entity.Enrollment;
import com.weolbu.eduplatform.entity.Member;
import com.weolbu.eduplatform.entity.Lecture;
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
    public Enrollment enrollLecture(Long memberId, Long lectureId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("Member not found"));
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new EntityNotFoundException("Lecture not found"));

        long curEnrollments = enrollmentRepository.countByLectureId(lectureId);
        if (curEnrollments >= lecture.getMaxStudents()) {
            throw new IllegalStateException("Lecture is already full");
        }

        Enrollment enrollment = Enrollment.builder().member(member).lecture(lecture).enrollmentDate(LocalDateTime.now())
                .build();

        return enrollmentRepository.save(enrollment);
    }
}
