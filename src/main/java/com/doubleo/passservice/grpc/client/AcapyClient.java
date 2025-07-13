package com.doubleo.passservice.grpc.client;

import com.doubleo.didagent.grpc.server.AcapyServiceGrpc;
import com.doubleo.didagent.grpc.server.VcIssueRequest;
import com.doubleo.didagent.grpc.server.VcIssueResponse;
import com.doubleo.hospitalservice.domain.area.grpc.server.*;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AcapyClient {

    @GrpcClient("did-agent")
    private AcapyServiceGrpc.AcapyServiceBlockingStub blockingStub;

    public boolean issueVc(String tenantId, Long passId, Long memberId) {

        log.info(
                "[AcapyClient] issueVc 호출됨 - tenantId: {}, passId: {}, memberId: {}",
                tenantId,
                passId,
                memberId);

        VcIssueRequest request =
                VcIssueRequest.newBuilder()
                        .setTenantId(tenantId)
                        .setPassId(passId)
                        .setMemberId(memberId)
                        .build();

        VcIssueResponse response = blockingStub.issueVc(request);

        log.info(
                "[AcapyClient] VC 발급 응답 - isInvitationCreated: {}",
                response.getIsInvitationCreated());

        return response.getIsInvitationCreated();
    }
}
