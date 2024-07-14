package com.weolbu.eduplatform.controller;

import org.springframework.web.bind.annotation.RestController;

import com.weolbu.eduplatform.dto.MemberRequestDto;
import com.weolbu.eduplatform.dto.MemberResponseDto;
import com.weolbu.eduplatform.service.MemberService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
@Tag(name = "Member", description = "Member management APIs")
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    @Operation(summary = "Register a new memeber", description = "Creates a new member with the provided details")
    public ResponseEntity<MemberResponseDto> registerMember(@Valid @RequestBody MemberRequestDto request) {
        return ResponseEntity.ok(memberService.registerMember(request));
    }
}
