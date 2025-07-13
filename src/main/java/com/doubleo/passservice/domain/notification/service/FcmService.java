package com.doubleo.passservice.domain.notification.service;

import com.doubleo.passservice.domain.notification.dto.request.FcmSendRequest;
import com.doubleo.passservice.domain.notification.repository.MemberNotificationRepository;
import com.google.firebase.messaging.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class FcmService {

    private final MemberNotificationRepository memberNotificationRepository;

    public void sendNotification(FcmSendRequest request) {
        if (request.token() == null || request.token().isEmpty()) {
            log.info("FCM token is null");
            return;
        }
        Message message =
                Message.builder()
                        .setToken(request.token())
                        .setNotification(
                                Notification.builder()
                                        .setTitle(request.title())
                                        .setBody(request.content())
                                        .build())
                        .setAndroidConfig(
                                AndroidConfig.builder()
                                        .setNotification(
                                                AndroidNotification.builder()
                                                        .setTitle(request.title())
                                                        .setBody(request.content())
                                                        .setClickAction("push_click")
                                                        .build())
                                        .build())
                        .setApnsConfig(
                                ApnsConfig.builder()
                                        .setAps(Aps.builder().setCategory("push_click").build())
                                        .build())
                        .build();

        try {
            String response = FirebaseMessaging.getInstance().send(message);
            log.info(response);
        } catch (FirebaseMessagingException e) {
            log.error(e.getMessage());
        }
    }
}
