package com.cuerpoactivo.cuerpo_activo_api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cuerpoactivo.cuerpo_activo_api.dto.SocioRequest;
import com.cuerpoactivo.cuerpo_activo_api.dto.SocioResponse;
import com.cuerpoactivo.cuerpo_activo_api.model.Socio;
import com.cuerpoactivo.cuerpo_activo_api.service.SocioService;

@RestController
@RequestMapping("/api/socios")
public class SocioController {

    private final SocioService socioService;

    public SocioController(SocioService socioService) {
        this.socioService = socioService;
    }

    @GetMapping
    public List<SocioResponse> listar() {
        return socioService.listar().stream()
                .map(SocioResponse::desde)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SocioResponse> buscar(@PathVariable Long id) {
        Optional<Socio> socio = socioService.buscarPorId(id);
        if (socio.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(SocioResponse.desde(socio.get()));
    }

    @PostMapping
    public ResponseEntity<SocioResponse> crear(@RequestBody SocioRequest datos) {
        Optional<Socio> creado = socioService.crear(datos);
        if (creado.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(SocioResponse.desde(creado.get()));
    }
}
