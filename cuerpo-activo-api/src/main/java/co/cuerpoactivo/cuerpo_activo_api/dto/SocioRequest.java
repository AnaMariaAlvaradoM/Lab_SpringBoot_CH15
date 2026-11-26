package co.cuerpoactivo.cuerpo_activo_api.dto;

import co.cuerpoactivo.cuerpo_activo_api.model.Plan;
import co.cuerpoactivo.cuerpo_activo_api.model.Socio;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record SocioRequest(
        @NotBlank(message = "El nombre es obligatorio")
        String nombre,

        @NotNull(message = "El plan es obligatorio")
        @Positive(message = "El ID del plan debe ser mayor que cero")
        Long planId
) {

    public Socio aEntidad(Plan plan) {
        Socio socio = new Socio();
        socio.setNombre(nombre);
        socio.setPlan(plan);
        return socio;
    }
}