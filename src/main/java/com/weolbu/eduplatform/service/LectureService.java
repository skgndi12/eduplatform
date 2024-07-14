package com.weolbu.eduplatform.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.weolbu.eduplatform.dto.LectureRequestDto;
import com.weolbu.eduplatform.dto.LectureResponseDto;
import com.weolbu.eduplatform.entity.Lecture;
import com.weolbu.eduplatform.entity.Member;
import com.weolbu.eduplatform.exception.CustomException;
import com.weolbu.eduplatform.repository.LectureRepository;
import com.weolbu.eduplatform.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LectureService {
    private final LectureRepository lectureRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public LectureResponseDto createLecture(LectureRequestDto request) {
        Member instructor = memberRepository.findById(request.getInstructorId())
                .orElseThrow(() -> new CustomException("Instructor not found"));

        Lecture lecture = Lecture.builder().name(request.getName()).maxStudents(request.getMaxStudents())
                .price(request.getPrice()).instructor(instructor).build();

        Lecture savedLecture = lectureRepository.save(lecture);
        return convertToResponseDto(savedLecture);
    }

    @Transactional(readOnly = true)
    public Page<LectureResponseDto> getAllLectures(Pageable pageable) {
        return lectureRepository.findAll(pageable).map(this::convertToResponseDto);
    }

    @Transactional(readOnly = true)
    public Page<LectureResponseDto> getLecturesByEnrollmentCount(Pageable pageable) {
        return lectureRepository.findAllOrderByEnrollmentCountDesc(pageable).map(this::convertToResponseDto);
    }

    @Transactional(readOnly = true)
    public Page<LectureResponseDto> getLecturesByEnrollmentRate(Pageable pageable) {
        return lectureRepository.findAllOrderByEnrollmentRateDesc(pageable).map(this::convertToResponseDto);
    }

    private LectureResponseDto convertToResponseDto(Lecture lecture) {
        LectureResponseDto response = new LectureResponseDto();
        response.setId(lecture.getId());
        response.setName(lecture.getName());
        response.setMaxStudents(lecture.getMaxStudents());
        response.setPrice(lecture.getPrice());
        response.setInstructorId(lecture.getInstructor().getId());
        response.setInstructorName(lecture.getInstructor().getName());
        return response;
    }
}
