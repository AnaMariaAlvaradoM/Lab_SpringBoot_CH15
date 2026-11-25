package co.cuerpoactivo.cuerpo_activo_api.dto;


import co.cuerpoactivo.cuerpo_activo_api.model.Socio;

public record SocioResponse(Long id, String nombre, String plan) {

    public static SocioResponse desde(Socio socio) {
        String plan = null;
        if (socio.getPlan() != null) {
            plan = socio.getPlan().getNombre();
        }
        return new SocioResponse(socio.getId(), socio.getNombre(), plan);
    }
}