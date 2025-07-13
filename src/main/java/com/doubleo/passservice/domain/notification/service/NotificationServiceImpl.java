package com.doubleo.passservice.domain.notification.service;

import com.doubleo.passservice.domain.notification.dto.response.MemberNotificationResponse;
import com.doubleo.passservice.domain.notification.repository.MemberNotificationRepository;
import com.doubleo.passservice.global.exception.CommonException;
import com.doubleo.passservice.global.exception.errorcode.NotificationErrorCode;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final MemberNotificationRepository memberNotificationRepository;
    private final Long SEVEN_DAYS = 7L;

    @Override
    public List<MemberNotificationResponse> getAllMemberNotifications(Long memberId) {
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(SEVEN_DAYS);

        return memberNotificationRepository
                .findAllByMemberIdAndCreatedDtAfter(memberId, sevenDaysAgo)
                .stream()
                .map(MemberNotificationResponse::from)
                .toList();
    }

    @Override
    public MemberNotificationResponse getRecentMemberNotification(Long memberId) {
        return memberNotificationRepository
                .findTopByMemberIdOrderByCreatedDtDesc(memberId)
                .map(MemberNotificationResponse::from)
                .orElseThrow(
                        () -> new CommonException(NotificationErrorCode.NOTIFICATION_NOT_FOUND));
    }

    @Override
    public void deleteAllMemberNotifications(Long memberId) {
        memberNotificationRepository.deleteAllByMemberId(memberId);
    }
}
