package co.cuerpoactivo.cuerpo_activo_api.exception;

import co.cuerpoactivo.cuerpo_activo_api.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class ManejadorDeErrores {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> manejarValidacion(MethodArgumentNotValidException ex) {

        Map<String, String> campos = new LinkedHashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            campos.put(error.getField(), error.getDefaultMessage());
        }

        ErrorResponse error = new ErrorResponse(400, "Hay datos inválidos en la solicitud", campos);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> manejarJsonIlegible(HttpMessageNotReadableException ex) {

        ErrorResponse error = new ErrorResponse(400, "El JSON no se pudo leer", Map.of());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<ErrorResponse> manejarNoEncontrado(RecursoNoEncontradoException ex) {

        ErrorResponse error = new ErrorResponse(404, ex.getMessage(), Map.of());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(ReglaDeNegocioException.class)
    public ResponseEntity<ErrorResponse> manejarReglaDeNegocio(ReglaDeNegocioException ex) {
        ErrorResponse error = new ErrorResponse(400, ex.getMessage(), Map.of());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
