package com.weolbu.eduplatform.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.weolbu.eduplatform.entity.Lecture;

public interface LectureRepository extends JpaRepository<Lecture, Long> {
    @Query("SELECT l FROM Lecture l LEFT JOIN l.enrollments e GROUP BY l ORDER BY COUNT(e) DESC")
    Page<Lecture> findAllOrderByEnrollmentCountDesc(Pageable pageable);

    @Query("SELECT l FROM Lecture l LEFT JOIN l.enrollments e GROUP BY l ORDER BY COUNT(e) * 1.0 / l.maxStudents DESC")
    Page<Lecture> findAllOrderByEnrollmentRateDesc(Pageable pageable);
}
