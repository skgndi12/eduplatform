package com.weolbu.eduplatform.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.weolbu.eduplatform.entity.Entollment;

public interface EnrollmentRepository extends JpaRepository<Entollment, Long> {
    long countByLectureId(Long lectureId);
}
