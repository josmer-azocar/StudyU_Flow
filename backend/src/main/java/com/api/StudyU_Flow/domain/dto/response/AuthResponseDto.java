package com.api.StudyU_Flow.domain.dto.response;

import lombok.Builder;

public record AuthResponseDto(
        String token
) {
    public static class Builder {
        private String token;

        public Builder token(String token) {
            this.token = token;
            return this;
        }

        public AuthResponseDto build() {
            return new AuthResponseDto(token);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
