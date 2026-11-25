package co.cuerpoactivo.cuerpo_activo_api.dto;

import java.util.List;

public record PlanResponse(
        Long id,
        String nombre,
        int duracionMeses,
        int precio,
        List<SocioResponse> socios
) {
}