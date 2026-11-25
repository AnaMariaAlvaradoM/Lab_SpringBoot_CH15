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
    public ResponseEntity<ErrorResponse> manejarValidacion(MethodArgumentNotValidException excepcion) {
        Map<String, String> errores = new TreeMap<>();
        for (FieldError item : excepcion.getBindingResult().getFieldErrors()) {
            errores.put(item.getField(), item.getDefaultMessage());
        }
        ErrorResponse cuerpo = new ErrorResponse(400, "Hay datos inválidos en la solicitud", errores);
        return ResponseEntity.badRequest().body(cuerpo);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> manejarJsonInvalido(HttpMessageNotReadableException excepcion) {
        ErrorResponse cuerpo = new ErrorResponse(400, "No se pudo leer el JSON: revisa los datos y sus tipos", Map.of());
        return ResponseEntity.badRequest().body(cuerpo);
    }

    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<ErrorResponse> noEncontrado(RecursoNoEncontradoException ex) {
        ErrorResponse error = new ErrorResponse(404, ex.getMessage(), Map.of());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(ReglaDeNegocioException.class)
    public ResponseEntity<ErrorResponse> reglaDeNegocio(ReglaDeNegocioException ex) {
        ErrorResponse error = new ErrorResponse(400, ex.getMessage(), Map.of());
        return ResponseEntity.badRequest().body(error);
    }
}