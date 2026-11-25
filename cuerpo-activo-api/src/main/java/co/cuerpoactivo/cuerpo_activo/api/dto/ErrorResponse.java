package co.cuerpoactivo.cuerpo_activo.api.dto;

import java.util.Map;

public record ErrorResponse(int status, String mensaje, Map<String, String> campos) {
}