package com.cuerpoactivo.cuerpo_activo_api.controller;


import com.cuerpoactivo.cuerpo_activo_api.dto.SocioRequest;
import com.cuerpoactivo.cuerpo_activo_api.dto.SocioResponse;
import com.cuerpoactivo.cuerpo_activo_api.service.SocioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public ResponseEntity<SocioResponse> buscarPorId(@PathVariable Long id) {
        SocioResponse socio = SocioResponse.desde(socioService.buscarPorId(id));
        return ResponseEntity.ok(socio);
    }

    @PostMapping
    public ResponseEntity<SocioResponse> crear(@Valid @RequestBody SocioRequest request) {
        SocioResponse creado = SocioResponse.desde(socioService.crear(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }
}
