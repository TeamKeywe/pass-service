package com.doubleo.passservice.grpc.client;

import com.doubleo.passservice.global.exception.GrpcExceptionUtil;
import com.doubleo.patientservice.domain.guardian.grpc.server.*;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GuardianClient {

    @GrpcClient("patient-service")
    private GuardianServiceGrpc.GuardianServiceBlockingStub blockingStub;

    public GuardianResponse getGuardianById(Long guardianId) {
        try {
            GuardianRequest request =
                    GuardianRequest.newBuilder().setGuardianId(guardianId).build();
            return blockingStub.getGuardian(request);
        } catch (StatusRuntimeException e) {
            log.error(e.getMessage());
            throw GrpcExceptionUtil.fromStatusRuntimeException(e);
        }
    }

    public PatientGuardianListResponse getPatientGuardianList(Long patientId) {
        try {
            PatientGuardianListRequest request =
                    PatientGuardianListRequest.newBuilder().setPatientId(patientId).build();
            return blockingStub.getPatientGuardianList(request);
        } catch (StatusRuntimeException e) {
            log.error(e.getMessage());
            throw GrpcExceptionUtil.fromStatusRuntimeException(e);
        }
    }

    public CreateGuardianResponse createGuardian(
            String tenantId, Long patientId, String guardianName, String guardianContact) {
        try {
            CreateGuardianRequest request =
                    CreateGuardianRequest.newBuilder()
                            .setTenantId(tenantId)
                            .setPatientId(patientId)
                            .setGuardianName(guardianName)
                            .setGuardianContact(guardianContact)
                            .build();
            return blockingStub.createGuardian(request);
        } catch (StatusRuntimeException e) {
            log.error(e.getMessage());
            throw GrpcExceptionUtil.fromStatusRuntimeException(e);
        }
    }
}
