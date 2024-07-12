package com.weolbu.eduplatform.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.weolbu.eduplatform.entity.Enrollment;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    long countByLectureId(Long lectureId);
}
