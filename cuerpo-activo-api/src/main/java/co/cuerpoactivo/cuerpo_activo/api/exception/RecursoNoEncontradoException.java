package co.cuerpoactivo.cuerpo_activo.api.exception;

public class RecursoNoEncontradoException extends RuntimeException{
    public RecursoNoEncontradoException(String mensaje){
        super(mensaje);
    }
}
