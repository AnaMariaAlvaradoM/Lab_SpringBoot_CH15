package co.cuerpoactivo.cuerpo_activo_api.dto;

import co.cuerpoactivo.cuerpo_activo_api.model.Plan;
import co.cuerpoactivo.cuerpo_activo_api.model.Socio;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record PlanRequest(
        @NotBlank(message = "El nombre del plan es obligatorio")
        String nombre,

        @Positive(message = "La duración en meses debe ser mayor que cero")
        int duracionMeses,

        @Positive(message = "El precio debe ser mayor a cero")
        int precio) {

    public Plan aEntidad() {
        Plan plan = new Plan();
        plan.setNombre(nombre);
        plan.setDuracionMeses(duracionMeses);
        plan.setPrecio(precio);
        return plan;
    }
}
