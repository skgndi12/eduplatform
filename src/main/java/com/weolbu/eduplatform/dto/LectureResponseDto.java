package com.weolbu.eduplatform.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class LectureResponseDto {
    private Long id;
    private String name;
    private Integer maxStudents;
    private BigDecimal price;
    private Long instructorId;
    private String instructorName;
}
