package com.weolbu.eduplatform.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class LectureResponseDto {
    private Long id;
    private String name;
    private Integer maxStudents;
    private BigDecimal price;
    private Long instructorId;
    private String instructorName;
}
