package co.cuerpoactivo.cuerpo_activo_api.exception;

import co.cuerpoactivo.cuerpo_activo_api.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.TreeMap;

@RestControllerAdvice
public class ManejadorDeErrores {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> datosInvalidos(MethodArgumentNotValidException excepcion) {
        Map<String, String> campos = new TreeMap<>();
        // Guardo el mensaje de cada campo para mostrarlo en el JSON.
        for (FieldError campo : excepcion.getBindingResult().getFieldErrors()) {
            campos.putIfAbsent(campo.getField(), campo.getDefaultMessage());
        }
        return responder(HttpStatus.BAD_REQUEST, "Hay datos inválidos en la solicitud", campos);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> jsonIlegible() {
        return responder(HttpStatus.BAD_REQUEST, "No se pudo leer el JSON: revisa los datos y sus tipos", Map.of());
    }

    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<ErrorResponse> noEncontrado(RecursoNoEncontradoException excepcion) {
        return responder(HttpStatus.NOT_FOUND, excepcion.getMessage(), Map.of());
    }

    @ExceptionHandler(ReglaDeNegocioException.class)
    public ResponseEntity<ErrorResponse> reglaDeNegocio(ReglaDeNegocioException excepcion) {
        return responder(HttpStatus.BAD_REQUEST, excepcion.getMessage(), Map.of());
    }

    private ResponseEntity<ErrorResponse> responder(HttpStatus estado, String mensaje, Map<String, String> campos) {
        return ResponseEntity.status(estado).body(new ErrorResponse(estado.value(), mensaje, campos));
    }
}
