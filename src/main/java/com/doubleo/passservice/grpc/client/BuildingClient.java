package com.doubleo.passservice.grpc.client;

import com.doubleo.hospitalservice.domain.building.grpc.server.BuildingRequest;
import com.doubleo.hospitalservice.domain.building.grpc.server.BuildingResponse;
import com.doubleo.hospitalservice.domain.building.grpc.server.BuildingServiceGrpc;
import com.doubleo.passservice.global.exception.GrpcExceptionUtil;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BuildingClient {

    @GrpcClient("hospital-service")
    private BuildingServiceGrpc.BuildingServiceBlockingStub blockingStub;

    public BuildingResponse getBuildingById(Long buildingId) {
        try {
            BuildingRequest request =
                    BuildingRequest.newBuilder().setBuildingId(buildingId).build();
            return blockingStub.getBuildingById(request);
        } catch (StatusRuntimeException e) {
            log.error(e.getMessage());
            throw GrpcExceptionUtil.fromStatusRuntimeException(e);
        }
    }
}
