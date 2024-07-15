package com.weolbu.eduplatform.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.weolbu.eduplatform.dto.EnrollmentRequestDto;
import com.weolbu.eduplatform.dto.EnrollmentResponseDto;
import com.weolbu.eduplatform.service.EnrollmentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/enrollments")
@RequiredArgsConstructor
@Tag(name = "Enrollment", description = "Enrollment management APIs")
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    @PostMapping
    @Operation(summary = "Enroll in a lecture", description = "Enrolls a student in a specific lecture")
    public ResponseEntity<EnrollmentResponseDto> enrollInLecture(@Valid @RequestBody EnrollmentRequestDto request) {
        return ResponseEntity.ok(enrollmentService.enrollInLecture(request));
    }
}
