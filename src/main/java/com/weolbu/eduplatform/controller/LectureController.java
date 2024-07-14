package com.weolbu.eduplatform.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.weolbu.eduplatform.dto.LectureRequestDto;
import com.weolbu.eduplatform.dto.LectureResponseDto;
import com.weolbu.eduplatform.service.LectureService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/v1/lectures")
@RequiredArgsConstructor
@Tag(name = "Lecture", description = "Lecture management APIs")
public class LectureController {
    private final LectureService lectureService;

    @PostMapping
    @Operation(summary = "Create a new lecture")
    public ResponseEntity<LectureResponseDto> createLecture(@Valid @RequestBody LectureRequestDto request) {
        return ResponseEntity.ok(lectureService.createLecture(request));
    }

    @GetMapping
    @Operation(summary = "Get all lectures")
    public ResponseEntity<PagedModel<EntityModel<LectureResponseDto>>> getAllLectures(
            @Parameter(description = "Pageable information for pagination. Only 'page' and 'size' parameters are supported. Example: page=0&size=20") @PageableDefault(size = 20) Pageable pageable,
            @Parameter(hidden = true) PagedResourcesAssembler<LectureResponseDto> assembler) {
        Page<LectureResponseDto> lectures = lectureService.getAllLectures(pageable);
        return ResponseEntity.ok(assembler.toModel(lectures));
    }

    @GetMapping("/by-enrollment-count")
    @Operation(summary = "Get lectures by enrollment count")
    public ResponseEntity<PagedModel<EntityModel<LectureResponseDto>>> getLecturesByEnrollmentCount(
            @Parameter(description = "Pageable information for pagination. Only 'page' and 'size' parameters are supported. Example: page=0&size=20") @PageableDefault(size = 20) Pageable pageable,
            @Parameter(hidden = true) PagedResourcesAssembler<LectureResponseDto> assembler) {
        Page<LectureResponseDto> lectures = lectureService.getLecturesByEnrollmentCount(pageable);
        return ResponseEntity.ok(assembler.toModel(lectures));
    }

    @GetMapping("/by-enrollment-rate")
    @Operation(summary = "Get lectures by enrollment rate")
    public ResponseEntity<PagedModel<EntityModel<LectureResponseDto>>> getLectureByEnrollmentRate(
            @Parameter(description = "Pageable information for pagination. Only 'page' and 'size' parameters are supported. Example: page=0&size=20") @PageableDefault(size = 20) Pageable pageable,
            @Parameter(hidden = true) PagedResourcesAssembler<LectureResponseDto> assembler) {
        Page<LectureResponseDto> lectures = lectureService.getLecturesByEnrollmentRate(pageable);
        return ResponseEntity.ok(assembler.toModel(lectures));
    }

}
