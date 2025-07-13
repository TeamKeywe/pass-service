package com.doubleo.passservice.domain.notification.service;

import com.doubleo.passservice.domain.notification.dto.response.MemberNotificationResponse;
import java.util.List;

public interface NotificationService {
    List<MemberNotificationResponse> getAllMemberNotifications(Long memberId);

    MemberNotificationResponse getRecentMemberNotification(Long memberId);

    void deleteAllMemberNotifications(Long memberId);
}
