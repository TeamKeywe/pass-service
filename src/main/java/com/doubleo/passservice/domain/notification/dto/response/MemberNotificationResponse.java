package com.doubleo.passservice.domain.notification.dto.response;

import com.doubleo.passservice.domain.notification.domain.MemberNotification;
import java.time.LocalDateTime;

public record MemberNotificationResponse(String title, String content, LocalDateTime createdAt) {
    public static MemberNotificationResponse from(MemberNotification notification) {
        return new MemberNotificationResponse(
                notification.getTitle(), notification.getContent(), notification.getCreatedDt());
    }
}
