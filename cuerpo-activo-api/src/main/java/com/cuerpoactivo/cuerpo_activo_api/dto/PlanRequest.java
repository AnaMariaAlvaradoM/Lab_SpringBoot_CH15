package com.cuerpoactivo.cuerpo_activo_api.dto;

import com.cuerpoactivo.cuerpo_activo_api.model.Plan;

public record PlanRequest(
        String nombre,
        int duracionMeses,
        int precio
) {
    public Plan aEntidad() {
        return new Plan(null, nombre, duracionMeses, precio);
    }
}