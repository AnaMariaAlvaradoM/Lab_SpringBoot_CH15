package co.cuerpoactivo.cuerpo_activo_api.dto;

import co.cuerpoactivo.cuerpo_activo_api.model.Plan;
import co.cuerpoactivo.cuerpo_activo_api.model.Socio;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SocioRequest(
        @NotBlank(message = "El nombre del socio es obligatorio")
        @Size(max = 60, message = "El nombre no puede pasar de 60 caracteres")
        String nombre,

        @NotNull(message = "Debes indicar el plan del socio")
        Long planId) {

    public Socio aEntidad(Plan plan) {
        Socio socio = new Socio();
        socio.setNombre(nombre);
        socio.setPlan(plan);
        return socio;
    }
}
