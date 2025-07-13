package com.doubleo.passservice.domain.pass.controller;

import com.doubleo.passservice.domain.pass.dto.request.UpdatePassStatusRequest;
import com.doubleo.passservice.domain.pass.dto.response.PassCreateResponse;
import com.doubleo.passservice.domain.pass.dto.response.PendingPassResponse;
import com.doubleo.passservice.domain.pass.service.PassService;
import com.doubleo.passservice.global.util.TenantValidator;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/passes")
@RequiredArgsConstructor
public class AdminPassController {

    private final PassService passService;
    private final TenantValidator tenantValidator;

    @Operation(summary = "Health Check API", description = "서비스 상태 확인 API")
    @GetMapping("/health")
    public String HealthCheck() {
        return "Pass Service is healthy";
    }

    @Operation(summary = "All pending pass get API", description = "모든 발급 대기중인 출입증 조회 API")
    @GetMapping("/pending")
    public Page<PendingPassResponse> PendingPassListGet(
            @RequestHeader("X-Admin-Id") Long adminId, Pageable pageable) {
        String tenantId = tenantValidator.getTenantId();
        return passService.getPendingPassList(tenantId, pageable);
    }

    @Operation(summary = "Accept Guardian application", description = "보호자 출입증 신청 승인 API")
    @PostMapping("/approve")
    public PassCreateResponse GuardianApplicationCreate(
            @RequestHeader("X-Admin-Id") Long adminId,
            @RequestBody UpdatePassStatusRequest request) {
        return passService.createGuardianAndUpdatePassStatus(
                request.passId(), request.issuanceStatus());
    }
}
