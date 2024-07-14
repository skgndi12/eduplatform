package com.weolbu.eduplatform.dto;

import lombok.Data;
import jakarta.validation.constraints.NotNull;

@Data
public class EnrollmentRequestDto {
    @NotNull(message = "Member ID is required")
    private Long memberId;

    @NotNull(message = "Lecture ID is required")
    private Long lectureId;
}
