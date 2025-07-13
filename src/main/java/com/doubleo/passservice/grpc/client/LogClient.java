package com.doubleo.passservice.grpc.client;

import com.doubleo.logservice.grpc.server.CreateIssuedLogRequest;
import com.doubleo.logservice.grpc.server.CreateIssuedLogResponse;
import com.doubleo.logservice.grpc.server.LogServiceGrpc;
import com.doubleo.passservice.domain.pass.enums.VisitCategory;
import com.doubleo.passservice.global.exception.GrpcExceptionUtil;
import com.doubleo.passservice.global.util.TimestampUtils;
import io.grpc.StatusRuntimeException;
import java.time.LocalDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LogClient {

    @GrpcClient("log-service")
    private LogServiceGrpc.LogServiceBlockingStub blockingStub;

    public CreateIssuedLogResponse createIssuedLog(
            String tenantId,
            Long memberId,
            String memberName,
            String memberContact,
            Long passId,
            LocalDateTime startAt,
            LocalDateTime expireAt,
            VisitCategory visitCategory,
            List<String> areaCodes) {
        try {
            CreateIssuedLogRequest request =
                    CreateIssuedLogRequest.newBuilder()
                            .setTenantId(tenantId)
                            .setMemberId(memberId)
                            .setMemberName(memberName)
                            .setMemberContact(memberContact)
                            .setPassId(passId)
                            .setStartAt(TimestampUtils.fromLocalDateTime(startAt))
                            .setExpiredAt(TimestampUtils.fromLocalDateTime(expireAt))
                            .setVisitCategory(visitCategory.toString())
                            .addAllAreaCodes(areaCodes)
                            .build();
            return blockingStub.createIssuedLog(request);
        } catch (StatusRuntimeException e) {
            log.error(e.getMessage());
            throw GrpcExceptionUtil.fromStatusRuntimeException(e);
        }
    }
}
