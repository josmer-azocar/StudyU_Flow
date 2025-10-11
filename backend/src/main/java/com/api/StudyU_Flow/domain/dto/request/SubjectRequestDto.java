package com.api.StudyU_Flow.domain.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record SubjectRequestDto(

        @NotBlank(message = "El nombre de la materia no puede estar vacío.")
        @Size(max = 100, message = "El nombre no puede exceder los 100 caracteres.")
        String name,

        @NotNull(message = "La cantidad de créditos es obligatoria.")
        @Min(value = 1, message = "La materia debe tener al menos 1 crédito.")
        Integer credits,

        @NotNull(message = "El número de semestre es obligatorio.")
        @Min(value = 1, message = "El semestre debe ser al menos 1.")
        Integer semester,

        @NotNull(message = "El ID de la carrera a la que pertenece es obligatorio.")
        @Min(value = 1, message = "El ID de la carrera no es válido.")
        Long idStudentDegree
) {
}
