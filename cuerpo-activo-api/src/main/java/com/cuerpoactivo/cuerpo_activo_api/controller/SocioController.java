package com.cuerpoactivo.cuerpo_activo_api.controller;

import com.cuerpoactivo.cuerpo_activo_api.dto.SocioRequest;
import com.cuerpoactivo.cuerpo_activo_api.dto.SocioResponse;
import com.cuerpoactivo.cuerpo_activo_api.service.SocioService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/api/socios")
public class SocioController {

    private final SocioService socioService;

    public SocioController(SocioService socioService) {
        this.socioService = socioService;
    }

    @GetMapping
    public List<SocioResponse> listarSocios() {
        return socioService.listar()
                .stream()
                .map(SocioResponse::desde)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SocioResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(
                SocioResponse.desde(socioService.buscarPorId(id)));
    }

    @PostMapping
    public ResponseEntity<SocioResponse> crearSocio(
            @Valid @RequestBody SocioRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(SocioResponse.desde(socioService.crear(request)));
    }
}