package co.cuerpoactivo.cuerpo_activo_api.dto;

import co.cuerpoactivo.cuerpo_activo_api.model.Plan;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record PlanRequest(
        @NotBlank(message = "El nombre es obligatorio")
        String nombre,

        @Positive(message = "La duracion en meses debe ser mayor que 0")
        int duracionMeses,

        @Positive(message = "El precio debe ser mayor a 0")
        int precio) {

    public Plan aEntidad() {
        Plan plan = new Plan();
        plan.setNombre(this.nombre);
        plan.setDuracionMeses(this.duracionMeses);
        plan.setPrecio(this.precio);
        return plan;
    }
}
