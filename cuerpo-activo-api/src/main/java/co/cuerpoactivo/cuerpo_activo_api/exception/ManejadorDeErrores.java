
package co.cuerpoactivo.cuerpo_activo_api.exception;

import co.cuerpoactivo.cuerpo_activo_api.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ManejadorDeErrores {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> manejarValidaciones(
            MethodArgumentNotValidException ex) {

        Map<String, String> campos = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                campos.put(error.getField(), error.getDefaultMessage())
        );

        ErrorResponse respuesta = new ErrorResponse(
                400,
                "Hay datos inválidos en la solicitud",
                campos
        );

        return ResponseEntity.badRequest().body(respuesta);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> manejarJson(
            HttpMessageNotReadableException ex) {

        ErrorResponse respuesta = new ErrorResponse(
                400,
                "El JSON enviado no es válido",
                Map.of()
        );

        return ResponseEntity.badRequest().body(respuesta);
    }

    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<ErrorResponse> manejarNoEncontrado(
            RecursoNoEncontradoException ex) {

        ErrorResponse respuesta = new ErrorResponse(
                404,
                ex.getMessage(),
                Map.of()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
    }

    @ExceptionHandler(ReglaDeNegocioException.class)
    public ResponseEntity<ErrorResponse> manejarReglaNegocio(
            ReglaDeNegocioException ex) {

        ErrorResponse respuesta = new ErrorResponse(
                400,
                ex.getMessage(),
                Map.of()
        );

        return ResponseEntity.badRequest().body(respuesta);
    }
}