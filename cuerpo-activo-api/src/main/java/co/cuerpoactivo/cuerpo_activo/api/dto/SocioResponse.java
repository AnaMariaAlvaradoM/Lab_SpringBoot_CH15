package co.cuerpoactivo.cuerpo_activo.api.dto;

import co.cuerpoactivo.cuerpo_activo.api.model.Socio;

public record SocioResponse(Long id, String nombre, String plan) {

    public static SocioResponse desde(Socio socio) {
        return new SocioResponse(
                socio.getId(),
                socio.getNombre(),
                socio.getPlan().getNombre()
        );
    }
}