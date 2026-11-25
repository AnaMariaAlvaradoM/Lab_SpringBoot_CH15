
package co.cuerpoactivo.cuerpo_activo.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class ManejadorDeErrores {

    // Manejar errores de validación de los campos
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> manejarValidacion(
            MethodArgumentNotValidException ex) {

        Map<String, String> campos = new LinkedHashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                campos.put(error.getField(), error.getDefaultMessage())
        );

        Map<String, Object> respuesta = Map.of(
                "status", HttpStatus.BAD_REQUEST.value(),
                "mensaje", "Hay datos inválidos en la solicitud",
                "campos", campos
        );

        return ResponseEntity.badRequest().body(respuesta);
    }

    // Manejar errores al leer el JSON
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> manejarJsonInvalido(
            HttpMessageNotReadableException ex) {

        Map<String, Object> respuesta = Map.of(
                "status", HttpStatus.BAD_REQUEST.value(),
                "mensaje", "El JSON enviado no se pudo leer",
                "campos", Map.of()
        );

        return ResponseEntity.badRequest().body(respuesta);
    }

    // Manejar recursos que no existen
    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> manejarRecursoNoEncontrado(
            RecursoNoEncontradoException ex) {

        Map<String, Object> respuesta = Map.of(
                "status", HttpStatus.NOT_FOUND.value(),
                "mensaje", ex.getMessage(),
                "campos", Map.of()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
    }

    // Manejar errores de reglas de negocio
    @ExceptionHandler(ReglaDeNegocioException.class)
    public ResponseEntity<Map<String, Object>> manejarReglaDeNegocio(
            ReglaDeNegocioException ex) {

        Map<String, Object> respuesta = Map.of(
                "status", HttpStatus.BAD_REQUEST.value(),
                "mensaje", ex.getMessage(),
                "campos", Map.of()
        );

        return ResponseEntity.badRequest().body(respuesta);
    }
}
