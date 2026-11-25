package co.cuerpoactivo.cuerpo_activo.api.exception;

public class ReglaDeNegocioException extends RuntimeException{
    public ReglaDeNegocioException(String mensaje){
        super(mensaje);
    }
}
