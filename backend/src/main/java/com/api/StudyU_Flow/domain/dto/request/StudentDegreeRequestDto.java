package com.api.StudyU_Flow.domain.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record StudentDegreeRequestDto(

        @NotNull(message = "El ID de la carrera a la que pertenece es obligatorio.")
        @Min(value = 1, message = "El ID de la carrera no es válido.")
        Long idDegree,

//        @NotNull(message = "El ID del Estudiante.")
//        @Min(value = 1, message = "El ID del Estudiante no es valido.")
//        Long idStudent,

        @NotNull(message = "La cantidad de semestres es obligatoria.")
        @Min(value = 1, message = "La carrera debe tener al menos 1 semestre.")
        Integer quantitySemesters,

        @NotNull(message = "El total de créditos es obligatorio.")
        @Min(value = 1, message = "La carrera debe tener al menos 1 crédito.")
        Integer totalCredits,

        @NotBlank(message = "El nombre de la universidad no puede estar vacío.")
        @Size(max = 100, message = "El nombre de la universidad no puede exceder los 100 caracteres.")
        String universityName
) {
}
