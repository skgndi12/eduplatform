package com.weolbu.eduplatform.repository;

import com.weolbu.eduplatform.entity.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureRepository extends JpaRepository<Lecture, Long> {
}
