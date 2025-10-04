package com.api.StudyU_Flow.domain.dto.request;

import lombok.Builder;

@Builder
public record RegisterRequestDto(
        String username,
        String password,
        String firstName,
        String lastName
) {
}
