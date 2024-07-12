package com.weolbu.eduplatform.service;

import org.springframework.stereotype.Service;

import com.weolbu.eduplatform.entity.Lecture;
import com.weolbu.eduplatform.repository.LectureRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LectureService {
    private final LectureRepository lectureRepository;

    @Transactional
    public Lecture createLecture(Lecture lecture) {
        return lectureRepository.save(lecture);
    }
}
