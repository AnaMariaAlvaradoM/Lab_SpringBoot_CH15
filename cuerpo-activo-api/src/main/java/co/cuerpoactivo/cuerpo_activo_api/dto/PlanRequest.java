package co.cuerpoactivo.cuerpo_activo_api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record PlanRequest(
        @NotBlank(message = "El nombre es obligatorio")
        String nombre,

        @Min(value = 1, message = "La duración debe ser de al menos 1 mes")
        int duracionMeses,

        @Positive(message = "El precio debe ser mayor a 0")
        int precio
) {
}