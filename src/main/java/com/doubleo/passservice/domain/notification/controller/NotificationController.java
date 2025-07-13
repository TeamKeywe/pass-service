package com.doubleo.passservice.domain.notification.controller;

import com.doubleo.passservice.domain.notification.dto.response.MemberNotificationResponse;
import com.doubleo.passservice.domain.notification.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/passes/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @Operation(summary = "All Member Notification get API", description = "모든 사용자의 7일 이내 알림 조회 API")
    @GetMapping
    public List<MemberNotificationResponse> MemberNotificationListGet(
            @RequestHeader("X-Member-Id") Long memberId) {
        return notificationService.getAllMemberNotifications(memberId);
    }

    @Operation(
            summary = "Recent one Member Notification get API",
            description = "사용자의 최근 하나의 알림 조회 API")
    @GetMapping("/recent")
    public MemberNotificationResponse RecentMemberNotificationGet(
            @RequestHeader("X-Member-Id") Long memberId) {
        return notificationService.getRecentMemberNotification(memberId);
    }

    @Operation(summary = "All Member Notifications delete API", description = "모든 사용자의 알림 삭제 API")
    @DeleteMapping
    public ResponseEntity<Void> MemberNotificationListDelete(
            @RequestHeader("X-Member-Id") Long memberId) {
        notificationService.deleteAllMemberNotifications(memberId);
        return ResponseEntity.ok().build();
    }
}
