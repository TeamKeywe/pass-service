package com.doubleo.passservice.domain.pass.dto.request;

import com.doubleo.passservice.domain.pass.enums.VisitCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PassCreateRequest(
        @Schema(description = "방문자 구분", example = "PATIENT") @NotNull VisitCategory visitCategory,
        @Schema(description = "환자 code", example = "A01010") String patientCode,
        @Schema(description = "시작 시간", example = "2007-12-03") @NotBlank String startAt) {}
