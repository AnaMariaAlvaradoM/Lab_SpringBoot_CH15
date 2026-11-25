package co.cuerpoactivo.cuerpo_activo_api.exception;

import co.cuerpoactivo.cuerpo_activo_api.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ManejadorDeErrores {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> manejarValidacion(MethodArgumentNotValidException ex) {
        Map<String, String> campos = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            campos.put(error.getField(), error.getDefaultMessage());
        }
        ErrorResponse respuesta = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Hay datos inválidos en la solicitud",
                campos
        );
        return ResponseEntity.badRequest().body(respuesta);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> manejarJsonInvalido(HttpMessageNotReadableException ex) {
        ErrorResponse respuesta = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "El cuerpo de la solicitud no es un JSON válido",
                Map.of()
        );
        return ResponseEntity.badRequest().body(respuesta);
    }

    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<ErrorResponse> manejarNoEncontrado(RecursoNoEncontradoException ex) {
        ErrorResponse respuesta = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                Map.of()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
    }

    @ExceptionHandler(ReglaDeNegocioException.class)
    public ResponseEntity<ErrorResponse> manejarReglaDeNegocio(ReglaDeNegocioException ex) {
        ErrorResponse respuesta = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                Map.of()
        );
        return ResponseEntity.badRequest().body(respuesta);
    }
}