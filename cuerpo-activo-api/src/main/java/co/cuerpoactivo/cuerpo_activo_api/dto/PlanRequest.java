package co.cuerpoactivo.cuerpo_activo_api.dto;

import co.cuerpoactivo.cuerpo_activo_api.model.Plan;
import co.cuerpoactivo.cuerpo_activo_api.model.Socio;

public record PlanRequest(String nombre, int duracionMeses, int precio) {

    public Plan aEntidad() {
        Plan plan = new Plan();
        plan.setNombre(nombre);
        plan.setDuracionMeses(duracionMeses);
        plan.setPrecio(precio);
        return plan;
    }
}
