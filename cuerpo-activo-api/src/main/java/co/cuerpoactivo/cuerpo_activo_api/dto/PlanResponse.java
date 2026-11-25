package co.cuerpoactivo.cuerpo_activo_api.dto;

import co.cuerpoactivo.cuerpo_activo_api.model.Plan;
import co.cuerpoactivo.cuerpo_activo_api.model.Socio;

import java.util.ArrayList;
import java.util.List;

public record PlanResponse(Long id, String nombre, int duracionMeses, int precio) {

        public static PlanResponse desde(Plan plan) {
            return new PlanResponse(plan.getId(), plan.getNombre(), plan.getDuracionMeses(), plan.getPrecio());
        }
}
