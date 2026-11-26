package com.cuerpoactivo.cuerpo_activo_api.controller;

import com.cuerpoactivo.cuerpo_activo_api.dto.SocioRequest;
import com.cuerpoactivo.cuerpo_activo_api.dto.SocioResponse;
import com.cuerpoactivo.cuerpo_activo_api.model.Socio;
import com.cuerpoactivo.cuerpo_activo_api.services.SocioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/socios")
public class SocioController {

    private final SocioService socioService;

    public SocioController(SocioService socioService) {
        this.socioService = socioService;
    }

    @GetMapping
    public List<SocioResponse> listar() {
        List<Socio> socios = socioService.listar();
        List<SocioResponse> respuesta = new ArrayList<>();

        for (Socio socio : socios) {
            respuesta.add(SocioResponse.desde(socio));
        }

        return respuesta;
    }

    @GetMapping("/{id}")
    public ResponseEntity<SocioResponse> buscarPorId(@PathVariable Long id) {
        Optional<Socio> socio = socioService.buscarPorId(id);
        if (socio.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(SocioResponse.desde(socio.get()));
    }

    @PostMapping
    public ResponseEntity<SocioResponse> crear(@Valid @RequestBody SocioRequest datos) {
        Optional<Socio> creado = socioService.crear(datos);
        if (creado.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(SocioResponse.desde(creado.get()));
    }
}