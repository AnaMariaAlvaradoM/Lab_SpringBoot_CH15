package co.cuerpoactivo.cuerpo_activo_api.dto;

public record SocioResponse(
        Long id,
        String nombre,
        Long planId,
        String plan
) {
}