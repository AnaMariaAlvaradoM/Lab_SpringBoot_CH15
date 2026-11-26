package com.cuerpoactivo.cuerpo_activo_api.exception;

import com.cuerpoactivo.cuerpo_activo_api.dto.ErrorResponse;
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
            String mensajeCampo = error.getDefaultMessage();
            if (mensajeCampo == null) {
                mensajeCampo = "Valor inválido";
            }
            campos.put(error.getField(), mensajeCampo);
        }

        ErrorResponse cuerpo = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Error de validación en los datos enviados",
                campos
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(cuerpo);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> manejarJsonMalFormado(HttpMessageNotReadableException ex) {
        ErrorResponse cuerpo = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "El JSON enviado no se pudo leer o tiene un formato inválido",
                Map.of()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(cuerpo);
    }

    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<ErrorResponse> manejarNoEncontrado(RecursoNoEncontradoException ex) {
        ErrorResponse cuerpo = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                Map.of()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(cuerpo);
    }

    @ExceptionHandler(ReglaDeNegocioException.class)
    public ResponseEntity<ErrorResponse> manejarReglaDeNegocio(ReglaDeNegocioException ex) {
        ErrorResponse cuerpo = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                Map.of()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(cuerpo);
    }
}