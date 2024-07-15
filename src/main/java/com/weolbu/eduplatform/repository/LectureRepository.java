package com.weolbu.eduplatform.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.weolbu.eduplatform.entity.Lecture;

import jakarta.persistence.LockModeType;

public interface LectureRepository extends JpaRepository<Lecture, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT l FROM Lecture l where l.id = :id")
    Optional<Lecture> findByIdWithLock(@Param("id") Long id);

    @Query("SELECT l FROM Lecture l LEFT JOIN l.enrollments e GROUP BY l ORDER BY COUNT(e) DESC")
    Page<Lecture> findAllOrderByEnrollmentCountDesc(Pageable pageable);

    @Query("SELECT l FROM Lecture l LEFT JOIN l.enrollments e GROUP BY l ORDER BY COUNT(e) * 1.0 / l.maxStudents DESC")
    Page<Lecture> findAllOrderByEnrollmentRateDesc(Pageable pageable);
}
