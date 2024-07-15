package com.weolbu.eduplatform.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.weolbu.eduplatform.dto.EnrollmentRequestDto;
import com.weolbu.eduplatform.entity.Lecture;
import com.weolbu.eduplatform.entity.Member;
import com.weolbu.eduplatform.entity.MemberType;
import com.weolbu.eduplatform.repository.EnrollmentRepository;
import com.weolbu.eduplatform.repository.LectureRepository;
import com.weolbu.eduplatform.repository.MemberRepository;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class EnrollmentServiceConcurrencyTest {

    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Test
    void enrollInLecture_ConcurrentEnrollment() throws InterruptedException {
        // Arrange
        Member instructor = Member.builder()
                .name("Instructor")
                .email("instructor@example.com")
                .phoneNumber("010-1111-2222")
                .password("password")
                .meberType(MemberType.INSTRUCTOR)
                .build();
        memberRepository.save(instructor);

        Lecture lecture = Lecture.builder()
                .name("Concurrent Programming 101")
                .maxStudents(10)
                .price(new BigDecimal("150.00"))
                .instructor(instructor)
                .build();
        lectureRepository.save(lecture);

        int numberOfThreads = 20;
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch endLatch = new CountDownLatch(numberOfThreads);
        AtomicInteger successfulEnrollments = new AtomicInteger(0);
        AtomicInteger failedEnrollments = new AtomicInteger(0);

        // Act
        for (int i = 0; i < numberOfThreads; i++) {
            final long studentId = i + 1;
            executorService.execute(() -> {
                try {
                    startLatch.await(); // 모든 스레드가 준비될 때까지 대기
                    Member student = Member.builder()
                            .name("Student" + studentId)
                            .email("student" + studentId + "@example.com")
                            .phoneNumber("010-1234-" + String.format("%04d", studentId))
                            .password("password")
                            .meberType(MemberType.STUDENT)
                            .build();
                    memberRepository.save(student);

                    EnrollmentRequestDto requestDto = new EnrollmentRequestDto();
                    requestDto.setMemberId(student.getId());
                    requestDto.setLectureId(lecture.getId());

                    enrollmentService.enrollInLecture(requestDto);
                    successfulEnrollments.incrementAndGet();
                } catch (Exception e) {
                    failedEnrollments.incrementAndGet();
                } finally {
                    endLatch.countDown();
                }
            });
        }

        startLatch.countDown(); // 모든 스레드 시작
        endLatch.await(); // 모든 스레드 완료 대기
        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.SECONDS);

        // Assert
        assertEquals(10, successfulEnrollments.get());
        assertEquals(10, failedEnrollments.get());

        long enrollmentCount = enrollmentRepository.countByLectureId(lecture.getId());
        assertEquals(10, enrollmentCount);
    }
}
