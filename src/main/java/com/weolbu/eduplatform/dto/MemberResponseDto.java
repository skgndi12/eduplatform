package com.weolbu.eduplatform.dto;

import com.weolbu.eduplatform.entity.MemberType;

import lombok.Data;

@Data
public class MemberResponseDto {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private MemberType memberType;
}
