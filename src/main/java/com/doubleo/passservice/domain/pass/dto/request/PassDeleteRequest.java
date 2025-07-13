package com.doubleo.passservice.domain.pass.dto.request;

import jakarta.validation.constraints.NotNull;

public record PassDeleteRequest(@NotNull Long passId) {}
