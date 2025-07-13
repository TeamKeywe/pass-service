package com.doubleo.passservice.grpc.client;

import com.doubleo.passservice.global.exception.GrpcExceptionUtil;
import com.doubleo.patientservice.domain.patient.grpc.server.*;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PatientClient {

    @GrpcClient("patient-service")
    private PatientServiceGrpc.PatientServiceBlockingStub blockingStub;

    public PatientResponse getPatientById(Long id) {
        try {
            PatientRequest request = PatientRequest.newBuilder().setPatientId(id).build();
            return blockingStub.getPatient(request);
        } catch (StatusRuntimeException e) {
            log.error(e.getMessage());
            throw GrpcExceptionUtil.fromStatusRuntimeException(e);
        }
    }

    public PatientResponse getPatientByPatientCode(String tenantId, String patientCode) {
        try {
            PatientByCode request =
                    PatientByCode.newBuilder()
                            .setTenantId(tenantId)
                            .setPatientCode(patientCode)
                            .build();
            return blockingStub.getPatientByCode(request);
        } catch (StatusRuntimeException e) {
            log.error(e.getMessage());
            throw GrpcExceptionUtil.fromStatusRuntimeException(e);
        }
    }

    public PatientResponse getPatientByNameAndRegNo(String tenantId, String name, String regNo) {
        try {
            PatientByNameAndRegNoRequest request =
                    PatientByNameAndRegNoRequest.newBuilder()
                            .setTenantId(tenantId)
                            .setPatientName(name)
                            .setPatientRegNo(regNo)
                            .build();
            return blockingStub.getPatientByNameAndRegNo(request);
        } catch (StatusRuntimeException e) {
            log.error(e.getMessage());
            throw GrpcExceptionUtil.fromStatusRuntimeException(e);
        }
    }
}
