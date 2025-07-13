package com.doubleo.passservice.grpc.client;

import com.doubleo.hospitalservice.domain.area.grpc.server.*;
import com.doubleo.passservice.global.exception.GrpcExceptionUtil;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AreaClient {

    @GrpcClient("hospital-service")
    private AreaServiceGrpc.AreaServiceBlockingStub blockingStub;

    public AreaResponse getAreaById(Long areaId) {
        try {
            AreaIdRequest request = AreaIdRequest.newBuilder().setAreaId(areaId).build();
            return blockingStub.getAreaById(request);
        } catch (StatusRuntimeException e) {
            log.warn(e.getMessage());
            throw GrpcExceptionUtil.fromStatusRuntimeException(e);
        }
    }

    public AreaFullNameResponse getAreaFullNameByCode(String tenantId, String areaCode) {
        try {
            AreaFullNameRequest request =
                    AreaFullNameRequest.newBuilder()
                            .setTenantId(tenantId)
                            .setAreaCode(areaCode)
                            .build();
            return blockingStub.getAreaFullNameByCode(request);
        } catch (StatusRuntimeException e) {
            log.warn(e.getMessage());
            throw GrpcExceptionUtil.fromStatusRuntimeException(e);
        }
    }
}
