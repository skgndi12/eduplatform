package com.weolbu.eduplatform.dto;

import com.weolbu.eduplatform.entity.MemberType;
import lombok.Data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Data
public class MemberRequestDto {
    @NotBlank(message = "Name is required")
    private String name;

    @Email
    @NotBlank(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\d{3}-\\d{4}-\\d{4}$")
    private String phoneNumber;

    @NotBlank(message = "Password is required")
    private String password;

    @NotNull(message = "Member type is required")
    private MemberType memberType;
}
