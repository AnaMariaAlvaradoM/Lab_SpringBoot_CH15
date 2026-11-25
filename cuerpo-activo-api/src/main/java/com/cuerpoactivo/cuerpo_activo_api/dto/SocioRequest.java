package com.cuerpoactivo.cuerpo_activo_api.dto;

import com.cuerpoactivo.cuerpo_activo_api.model.Socio;
import com.cuerpoactivo.cuerpo_activo_api.model.Plan;

public record SocioRequest(
        String nombre,
        Long planId
) {
    public Socio aEntidad(Plan plan) {
        return new Socio(null, nombre, plan);
    }
}