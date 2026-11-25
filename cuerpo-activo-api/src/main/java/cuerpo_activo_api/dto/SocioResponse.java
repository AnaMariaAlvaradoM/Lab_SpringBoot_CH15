package cuerpo_activo_api.dto;

import cuerpo_activo_api.model.Socio;

public record SocioResponse(Long id, String nombre, String plan) {
    public static SocioResponse desde(Socio socio) {
        return new SocioResponse(socio.getId(), socio.getNombre(), socio.getPlan().getNombre());
    }
}