package com.weolbu.eduplatform.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.weolbu.eduplatform.entity.Enrollment;
import com.weolbu.eduplatform.entity.Lecture;
import com.weolbu.eduplatform.entity.Member;
import com.weolbu.eduplatform.entity.MemberType;

@DataJpaTest
class LectureRepositoryTest {

    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Test
    void findAllOrderByEnrollmentCountDesc_Success() {
        // Arrange
        Member instructor = Member.builder()
                .name("John Doe")
                .email("john@example.com")
                .phoneNumber("010-1234-5678")
                .password("password")
                .meberType(MemberType.INSTRUCTOR)
                .build();
        memberRepository.save(instructor);

        Lecture lecture1 = Lecture.builder()
                .name("Java 101")
                .maxStudents(30)
                .price(new BigDecimal("100.00"))
                .instructor(instructor)
                .build();
        lectureRepository.save(lecture1);

        Lecture lecture2 = Lecture.builder()
                .name("Python 101")
                .maxStudents(20)
                .price(new BigDecimal("80.00"))
                .instructor(instructor)
                .build();
        lectureRepository.save(lecture2);

        // Create enrollments
        for (int i = 0; i < 5; i++) {
            Enrollment enrollment = new Enrollment();
            enrollment.setLecture(lecture1);
            enrollment.setEnrollmentDate(LocalDateTime.now());
            enrollmentRepository.save(enrollment);
        }

        for (int i = 0; i < 3; i++) {
            Enrollment enrollment = new Enrollment();
            enrollment.setLecture(lecture2);
            enrollment.setEnrollmentDate(LocalDateTime.now());
            enrollmentRepository.save(enrollment);
        }

        Pageable pageable = PageRequest.of(0, 10);

        // Act
        Page<Lecture> result = lectureRepository.findAllOrderByEnrollmentCountDesc(pageable);

        // Assert
        List<Lecture> lectures = result.getContent();
        assertEquals(2, lectures.size());
        assertEquals("Java 101", lectures.get(0).getName()); // Should be first due to more enrollments
        assertEquals("Python 101", lectures.get(1).getName());
    }

    @Test
    void findAllOrderByEnrollmentRateDesc_Success() {
        // Arrange
        Member instructor = Member.builder()
                .name("Jane Doe")
                .email("jane@example.com")
                .phoneNumber("010-9876-5432")
                .password("password")
                .meberType(MemberType.INSTRUCTOR)
                .build();
        memberRepository.save(instructor);

        Lecture lecture1 = Lecture.builder()
                .name("Java 101")
                .maxStudents(30)
                .price(new BigDecimal("100.00"))
                .instructor(instructor)
                .build();
        lectureRepository.save(lecture1);

        Lecture lecture2 = Lecture.builder()
                .name("Python 101")
                .maxStudents(10)
                .price(new BigDecimal("80.00"))
                .instructor(instructor)
                .build();
        lectureRepository.save(lecture2);

        // Create enrollments
        for (int i = 0; i < 15; i++) {
            Enrollment enrollment = new Enrollment();
            enrollment.setLecture(lecture1);
            enrollment.setEnrollmentDate(LocalDateTime.now());
            enrollmentRepository.save(enrollment);
        }

        for (int i = 0; i < 8; i++) {
            Enrollment enrollment = new Enrollment();
            enrollment.setLecture(lecture2);
            enrollment.setEnrollmentDate(LocalDateTime.now());
            enrollmentRepository.save(enrollment);
        }

        Pageable pageable = PageRequest.of(0, 10);

        // Act
        Page<Lecture> result = lectureRepository.findAllOrderByEnrollmentRateDesc(pageable);

        // Assert
        List<Lecture> lectures = result.getContent();
        assertEquals(2, lectures.size());
        assertEquals("Python 101", lectures.get(0).getName()); // Should be first due to higher enrollment rate
        assertEquals("Java 101", lectures.get(1).getName());
    }
}
