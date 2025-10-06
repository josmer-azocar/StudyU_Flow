package com.api.StudyU_Flow.domain.dto.request;

import com.api.StudyU_Flow.domain.Enum.Gender;
import jakarta.validation.constraints.*;
import lombok.Builder;
import java.time.LocalDate;

@Builder
public record StudentRequestDto(
        @NotBlank(message = "El nombre de usuario es obligatorio.")
        @Size(max = 100, message = "El nombre de usuario no puede exceder los 100 caracteres.")
        String username,

        @NotBlank(message = "La contraseña es obligatoria.")
        @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres.")
        String password,

        @NotBlank(message = "El nombre es obligatorio.")
        @Size(max = 25, message = "El nombre no puede exceder los 25 caracteres.")
        String firstName,

        @NotBlank(message = "El apellido es obligatorio.")
        @Size(max = 25, message = "El apellido no puede exceder los 25 caracteres.")
        String lastName,

        @NotNull(message = "La fecha de nacimiento es obligatoria.")
        LocalDate dateOfBirth,

        @NotNull
        Gender gender
) {
}
