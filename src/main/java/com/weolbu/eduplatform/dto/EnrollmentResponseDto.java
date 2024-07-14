package com.weolbu.eduplatform.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class EnrollmentResponseDto {
    private Long id;
    private Long memberId;
    private String memberName;
    private Long lectureId;
    private String lectureName;
    private LocalDateTime enrollmentDate;
}
