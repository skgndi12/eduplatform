package com.weolbu.eduplatform.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class EnrollmentResponseDto {
    private Long id;
    private Long memberId;
    private String memberName;
    private Long lectureId;
    private String lectureName;
    private LocalDateTime enrollmentDate;
}
