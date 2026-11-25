package co.cuerpoactivo.cuerpo_activo_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SocioRequest(
        @NotBlank(message = "El nombre es obligatorio") String nombre,
        @NotNull(message = "El plan es obligatorio") Long planId) {
}