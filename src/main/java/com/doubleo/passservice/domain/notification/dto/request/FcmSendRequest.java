package com.doubleo.passservice.domain.notification.dto.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;

public record FcmSendRequest(
        @Nullable String token, @NotBlank String title, @NotBlank String content) {}
