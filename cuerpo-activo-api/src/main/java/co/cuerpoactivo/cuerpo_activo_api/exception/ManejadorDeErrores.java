
package co.cuerpoactivo.cuerpo_activo_api.exception;

import co.cuerpoactivo.cuerpo_activo_api.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ManejadorDeErrores {

    // Error 404: recurso no encontrado
    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<ErrorResponse> manejarNoEncontrado(
            RecursoNoEncontradoException ex) {

        ErrorResponse error = new ErrorResponse(
                404,
                ex.getMessage(),
                Map.of()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    // Error 400: datos que no cumplen las validaciones
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> manejarValidaciones(
            MethodArgumentNotValidException ex) {

        Map<String, String> campos = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        error -> error.getField(),
                        error -> error.getDefaultMessage(),
                        (mensaje1, mensaje2) -> mensaje1
                ));

        ErrorResponse error = new ErrorResponse(
                400,
                "Error de validación en los datos enviados",
                campos
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    // Error 400: JSON incorrecto o ilegible
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> manejarJsonInvalido(
            HttpMessageNotReadableException ex) {

        ErrorResponse error = new ErrorResponse(
                400,
                "El JSON enviado no tiene un formato válido",
                Map.of()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    // Error 400: regla de negocio incumplida
    @ExceptionHandler(ReglaDeNegocioException.class)
    public ResponseEntity<ErrorResponse> manejarReglaDeNegocio(
            ReglaDeNegocioException ex) {

        ErrorResponse error = new ErrorResponse(
                400,
                ex.getMessage(),
                Map.of()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
