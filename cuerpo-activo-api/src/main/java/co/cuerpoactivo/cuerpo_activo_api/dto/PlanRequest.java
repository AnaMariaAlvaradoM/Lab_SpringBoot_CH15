package co.cuerpoactivo.cuerpo_activo_api.dto;

import co.cuerpoactivo.cuerpo_activo_api.model.Plan;

public record PlanRequest(String nombre, int duracionMeses, int precio) {
    public Plan aEntidad() {
        return new Plan(null, nombre, duracionMeses, precio);
    }
}