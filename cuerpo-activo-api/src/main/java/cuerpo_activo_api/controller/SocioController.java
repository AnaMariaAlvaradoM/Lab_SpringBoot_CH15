package cuerpo_activo_api.controller;

import cuerpo_activo_api.dto.SocioRequest;
import cuerpo_activo_api.dto.SocioResponse;
import cuerpo_activo_api.service.SocioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return socioService.listar().stream().map(SocioResponse::desde).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SocioResponse> buscar(@PathVariable Long id) {
        return socioService.buscarPorId(id)
                .map(socio -> ResponseEntity.ok(SocioResponse.desde(socio)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SocioResponse> crear(@RequestBody SocioRequest request) {
        return socioService.crear(request)
                .map(socio -> ResponseEntity.status(HttpStatus.CREATED).body(SocioResponse.desde(socio)))
                .orElse(ResponseEntity.badRequest().build());
    }
}