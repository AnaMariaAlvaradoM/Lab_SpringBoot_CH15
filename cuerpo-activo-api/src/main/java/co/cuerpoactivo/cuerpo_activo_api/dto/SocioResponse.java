package co.cuerpoactivo.cuerpo_activo_api.dto;

import co.cuerpoactivo.cuerpo_activo_api.model.Plan;
import co.cuerpoactivo.cuerpo_activo_api.model.Socio;
import co.cuerpoactivo.cuerpo_activo_api.dto.SocioRequest;

public record SocioResponse(Long id, String nombre, String plan) {
    public static SocioResponse desde(Socio socio) {
        return new SocioResponse(socio.getId(), socio.getNombre(), socio.getPlan().getNombre());
    }
}
