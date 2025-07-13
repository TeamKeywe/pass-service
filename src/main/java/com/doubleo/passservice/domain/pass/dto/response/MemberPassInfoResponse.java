package com.doubleo.passservice.domain.pass.dto.response;

import com.doubleo.passservice.domain.pass.dto.AreaInfo;
import com.doubleo.passservice.domain.pass.dto.GuardianInfo;
import com.doubleo.passservice.domain.pass.enums.IssuanceStatus;
import com.doubleo.passservice.domain.pass.enums.VisitCategory;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record MemberPassInfoResponse(
        Long passId,
        Long memberId,
        Long hospitalId,
        List<AreaInfo> accessAreas,
        VisitCategory visitCategory,
        Long patientId,
        String patientName,
        List<GuardianInfo> guardians,
        IssuanceStatus issuanceStatus,
        LocalDateTime startedAt,
        LocalDateTime expiredAt) {}
