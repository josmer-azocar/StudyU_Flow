package com.api.StudyU_Flow.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record DegreeRequestDto(

        @NotBlank(message = "El nombre de la carrera no puede estar vacío.")
        @Size(max = 100, message = "El nombre no puede exceder los 100 caracteres.")
        String name


) {
}
