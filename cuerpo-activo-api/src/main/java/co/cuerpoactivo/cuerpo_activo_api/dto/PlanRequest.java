package co.cuerpoactivo.cuerpo_activo_api.dto;

import co.cuerpoactivo.cuerpo_activo_api.model.Plan;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record PlanRequest(
        @NotBlank(message = "El nombre del plan es obligatorio")
        @Size(max = 60, message = "El nombre no puede pasar de 60 caracteres")
        String nombre,

        @Positive(message = "La duracion debe ser mayor que cero")
        int duracionMeses,

        @Positive(message = "El precio debe ser mayor que cero")
        int precio) {

    public Plan aEntidad() {
        Plan plan = new Plan();
        plan.setNombre(nombre);
        plan.setDuracionMeses(duracionMeses);
        plan.setPrecio(precio);
        return plan;
    }
}
