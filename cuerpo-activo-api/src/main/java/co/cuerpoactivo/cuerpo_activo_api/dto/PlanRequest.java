package co.cuerpoactivo.cuerpo_activo_api.dto;

import co.cuerpoactivo.cuerpo_activo_api.model.Plan;

public record PlanRequest(String nombre, int duracionMeses, int Precio) {
    public Plan aEntidad(){
        Plan planes = new Plan();
        planes.setNombre(nombre);
        planes.setDuracionMeses(duracionMeses);
        planes.setPrecio(Precio);
        return planes;
    }
}
