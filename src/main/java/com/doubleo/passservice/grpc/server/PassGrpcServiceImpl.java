package com.doubleo.passservice.grpc.server;

import com.doubleo.memberservice.domain.member.grpc.server.MemberResponse;
import com.doubleo.passservice.domain.notification.domain.MemberNotification;
import com.doubleo.passservice.domain.notification.dto.request.FcmSendRequest;
import com.doubleo.passservice.domain.notification.repository.MemberNotificationRepository;
import com.doubleo.passservice.domain.notification.service.FcmService;
import com.doubleo.passservice.domain.pass.domain.Pass;
import com.doubleo.passservice.domain.pass.domain.PassArea;
import com.doubleo.passservice.domain.pass.enums.IssuanceStatus;
import com.doubleo.passservice.domain.pass.enums.VisitCategory;
import com.doubleo.passservice.domain.pass.repository.PassAreaRepository;
import com.doubleo.passservice.domain.pass.repository.PassRepository;
import com.doubleo.passservice.global.exception.CommonException;
import com.doubleo.passservice.global.exception.errorcode.PassErrorCode;
import com.doubleo.passservice.grpc.client.LogClient;
import com.doubleo.passservice.grpc.client.MemberClient;
import com.doubleo.passservice.grpc.client.PatientClient;
import com.doubleo.passservice.grpc.server.PassServiceGrpc.PassServiceImplBase;
import com.doubleo.patientservice.domain.patient.grpc.server.PatientResponse;
import io.grpc.stub.StreamObserver;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

@Slf4j
@GrpcService
@RequiredArgsConstructor
public class PassGrpcServiceImpl extends PassServiceImplBase {

    private final PassRepository passRepository;
    private final LogClient logClient;
    private final MemberClient memberClient;
    private final PassAreaRepository passAreaRepository;
    private final FcmService fcmService;
    private final PatientClient patientClient;
    private final MemberNotificationRepository memberNotificationRepository;

    public static final String GUARDIAN_APPROVED_NOTIFICATION_TITLE = "보호자 신청 승인";
    public static final String GUARDIAN_APPROVED_NOTIFICATION_CONTENT = "%s님의 보호자 신청이 승인되었습니다.";

    @Override
    public void updateConnectionState(
            UpdateConnectionStatusRequest request,
            StreamObserver<UpdateConnectionStatusResponse> responseObserver) {
        String tenantId = request.getTenantId();
        long passId = request.getPassId();
        String connectionId = request.getConnectionId();

        Pass pass =
                passRepository
                        .findById(passId)
                        .orElseThrow(() -> new CommonException(PassErrorCode.PASS_NOT_FOUND));

        MemberResponse member = memberClient.getMemberById(pass.getMemberId());

        List<String> areaCodes =
                passAreaRepository.findAllByPass(pass).stream().map(PassArea::getAreaCode).toList();

        PatientResponse patient = patientClient.getPatientById(pass.getPatientId());

        UpdateConnectionStatusResponse response;

        try {

            pass.updateDidConnectionId(connectionId);
            pass.updateStatus(IssuanceStatus.ISSUED);
            passRepository.save(pass);

            response =
                    UpdateConnectionStatusResponse.newBuilder()
                            .setTenantId(tenantId)
                            .setPassId(pass.getId())
                            .setMemberId(pass.getMemberId())
                            .setConnectionId(connectionId)
                            .setIsUpdated(true)
                            .build();

        } catch (Exception e) {
            log.warn("updateConnectionState 실패: {}", e.getMessage());
            response =
                    UpdateConnectionStatusResponse.newBuilder()
                            .setTenantId(tenantId)
                            .setPassId(passId)
                            .setConnectionId(connectionId)
                            .setIsUpdated(false)
                            .build();
        }

        try {
            logClient.createIssuedLog(
                    pass.getTenantId(),
                    pass.getMemberId(),
                    member.getMemberName(),
                    member.getMemberContact(),
                    pass.getId(),
                    pass.getStartAt(),
                    pass.getExpiredAt(),
                    pass.getVisitCategory(),
                    areaCodes);
        } catch (Exception e) {
            log.error("로그 전송 실패: {}", e.getMessage());
        }

        MemberResponse patientMember = null;
        try {
            patientMember =
                    memberClient.getMemberByNameAndRegNo(patient.getName(), patient.getRegNo());
        } catch (Exception e) {
            log.warn("환자 멤버가 존재하지 않아 알림을 생략합니다.");
        }

        if (pass.getVisitCategory() == VisitCategory.GUARDIAN) {
            fcmService.sendNotification(
                    new FcmSendRequest(
                            member.getFcmToken(),
                            GUARDIAN_APPROVED_NOTIFICATION_TITLE,
                            String.format(
                                    GUARDIAN_APPROVED_NOTIFICATION_CONTENT,
                                    member.getMemberName())));
            memberNotificationRepository.save(
                    MemberNotification.createMemberNotification(
                            member.getMemberId(),
                            GUARDIAN_APPROVED_NOTIFICATION_TITLE,
                            String.format(
                                    GUARDIAN_APPROVED_NOTIFICATION_CONTENT,
                                    member.getMemberName())));
            if (patientMember != null) {
                fcmService.sendNotification(
                        new FcmSendRequest(
                                patientMember.getFcmToken(),
                                GUARDIAN_APPROVED_NOTIFICATION_TITLE,
                                String.format(
                                        GUARDIAN_APPROVED_NOTIFICATION_CONTENT,
                                        member.getMemberName())));
                memberNotificationRepository.save(
                        MemberNotification.createMemberNotification(
                                patientMember.getMemberId(),
                                GUARDIAN_APPROVED_NOTIFICATION_TITLE,
                                String.format(
                                        GUARDIAN_APPROVED_NOTIFICATION_CONTENT,
                                        member.getMemberName())));
            }
        }
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getConnectionIdByPassId(
            GetConnectionIdByPassIdRequest request,
            StreamObserver<GetConnectionIdByPassIdResponse> responseObserver) {
        String tenantId = request.getTenantId();
        long passId = request.getPassId();

        try {
            Pass pass =
                    passRepository
                            .findById(passId)
                            .orElseThrow(() -> new CommonException(PassErrorCode.PASS_NOT_FOUND));

            String connectionId = pass.getDidConnectionId();
            if (connectionId == null) {
                throw new CommonException(PassErrorCode.CONNECTION_ID_NOT_ASSIGNED);
            }

            GetConnectionIdByPassIdResponse response =
                    GetConnectionIdByPassIdResponse.newBuilder()
                            .setConnectionId(connectionId)
                            .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();

        } catch (Exception e) {
            log.error(
                    "[{}] connectionId 조회 실패 - passId: {}, error: {}",
                    tenantId,
                    passId,
                    e.getMessage());
            responseObserver.onError(e);
        }
    }
}
