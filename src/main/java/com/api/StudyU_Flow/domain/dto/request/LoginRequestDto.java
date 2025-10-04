package com.api.StudyU_Flow.domain.dto.request;

import lombok.Builder;

@Builder
public record LoginRequestDto(
        String username,
        String password
) {

}
