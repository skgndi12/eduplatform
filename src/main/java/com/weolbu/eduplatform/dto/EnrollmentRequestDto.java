package com.weolbu.eduplatform.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EnrollmentRequestDto {
    @NotNull(message = "Member ID is required")
    private Long memberId;

    @NotNull(message = "Lecture ID is required")
    private Long lectureId;
}
