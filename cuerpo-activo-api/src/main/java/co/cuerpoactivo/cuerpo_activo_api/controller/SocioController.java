package co.cuerpoactivo.cuerpo_activo_api.controller;

import co.cuerpoactivo.cuerpo_activo_api.dto.SocioRequest;
import co.cuerpoactivo.cuerpo_activo_api.dto.SocioResponse;
import co.cuerpoactivo.cuerpo_activo_api.service.SocioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/socios")
public class SocioController {

    private final SocioService socioService;

    public SocioController(SocioService socioService) {
        this.socioService = socioService;
    }

    @GetMapping
    public List<SocioResponse> obtenerTodos() {
        return socioService.buscarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SocioResponse> obtenerPorId(@PathVariable Long id) {
        SocioResponse socio = socioService.buscarPorId(id)
                .orElseThrow(() -> new NoSuchElementException("Socio no encontrado con id " + id));
        return ResponseEntity.ok(socio);
    }

    @PostMapping
    public ResponseEntity<SocioResponse> crearSocio(@Valid @RequestBody SocioRequest request) {
        SocioResponse creado = socioService.crear(request)
                .orElseThrow(() -> new IllegalArgumentException("El plan con id " + request.planId() + " no existe"));
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SocioResponse> actualizarSocio(@PathVariable Long id, @Valid @RequestBody SocioRequest request) {
        SocioResponse actualizado = socioService.actualizar(id, request)
                .orElseThrow(() -> new NoSuchElementException("Socio o plan no encontrado para actualizar"));
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarSocio(@PathVariable Long id) {
        if (!socioService.eliminar(id)) {
            throw new NoSuchElementException("Socio no encontrado con id " + id);
        }
        return ResponseEntity.noContent().build();
    }
}