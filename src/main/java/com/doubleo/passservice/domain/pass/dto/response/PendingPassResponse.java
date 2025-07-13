package com.doubleo.passservice.domain.pass.dto.response;

import java.time.LocalDateTime;

public record PendingPassResponse(
        Long passId,
        Long memberId,
        String patientCode,
        String patientName,
        String guardianName,
        String guardianContact,
        LocalDateTime createdDt,
        LocalDateTime startAt,
        LocalDateTime expiredAt) {}
