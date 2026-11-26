package co.cuerpoactivo.cuerpo_activo_api.dto;

import co.cuerpoactivo.cuerpo_activo_api.model.Plan;
import co.cuerpoactivo.cuerpo_activo_api.model.Socio;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PlanResponse(Long id, String nombre, int duracionMeses, int precio) {
    public static PlanResponse desde(Plan plan) {
        return new PlanResponse(plan.getId(), plan.getNombre(), plan.getDuracionMeses(), plan.getPrecio());
    }
}
