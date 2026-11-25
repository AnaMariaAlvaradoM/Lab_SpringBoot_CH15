package com.cuerpoactivo.cuerpo_activo_api.dto;

import com.cuerpoactivo.cuerpo_activo_api.model.Socio;
import com.cuerpoactivo.cuerpo_activo_api.model.Plan;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SocioRequest(

        @NotBlank(message = "El nombre es obligatorio")
        String nombre,

        @NotNull(message = "El plan es obligatorio")
        Long planId

) {
    public Socio aEntidad(Plan plan) {
        return new Socio(null, nombre, plan);
    }
}