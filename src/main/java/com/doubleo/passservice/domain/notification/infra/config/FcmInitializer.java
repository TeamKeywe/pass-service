package com.doubleo.passservice.domain.notification.infra.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FcmInitializer {

    @Value("${firebase.key-path}")
    String fcmKeyPath;

    @PostConstruct
    public void init() {
        try {
            if (FirebaseApp.getApps().isEmpty()) {
                InputStream refreshToken = new ClassPathResource(fcmKeyPath).getInputStream();
                FirebaseOptions options =
                        FirebaseOptions.builder()
                                .setCredentials(GoogleCredentials.fromStream(refreshToken))
                                .build();

                FirebaseApp.initializeApp(options);
                log.info("FirebaseApp initialized");
            } else {
                log.info("FirebaseApp is already running");
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
