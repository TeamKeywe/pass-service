package com.doubleo.passservice.domain.pass.service;

import com.doubleo.passservice.domain.pass.dto.response.MemberPassInfoResponse;
import com.doubleo.passservice.domain.pass.dto.response.PassCreateResponse;
import com.doubleo.passservice.domain.pass.dto.response.PendingPassResponse;
import com.doubleo.passservice.domain.pass.enums.IssuanceStatus;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PassService {
    List<MemberPassInfoResponse> getAllMemberPassInfo(Long memberId);

    PassCreateResponse createPatientPass(
            Long memberId, Long hospitalId, String tenantId, String startAtInput);

    PassCreateResponse createGuardianPass(
            Long memberId,
            Long hospitalId,
            String tenantId,
            String patientCode,
            String startAtInput);

    void deletePass(Long passId);

    Page<PendingPassResponse> getPendingPassList(String tenantId, Pageable pageable);

    PassCreateResponse createGuardianAndUpdatePassStatus(
            Long passId, IssuanceStatus issuanceStatus);
}
