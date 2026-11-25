package co.cuerpoactivo.cuerpo_activo_api.dto;

public record ErrorResponse(int status, String mensaje, Map<String, String> campos) { }
