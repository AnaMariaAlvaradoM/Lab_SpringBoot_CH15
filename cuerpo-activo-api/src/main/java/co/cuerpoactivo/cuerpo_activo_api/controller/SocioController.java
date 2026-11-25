
package co.cuerpoactivo.cuerpo_activo_api.controller;

import co.cuerpoactivo.cuerpo_activo_api.dto.SocioRequest;
import co.cuerpoactivo.cuerpo_activo_api.dto.SocioResponse;
import co.cuerpoactivo.cuerpo_activo_api.service.SocioService;

import jakarta.validation.Valid;

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
        return socioService.listar()
                .stream()
                .map(SocioResponse::desde)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SocioResponse> buscarPorId(
            @PathVariable Long id) {

        SocioResponse respuesta = SocioResponse.desde(
                socioService.buscarPorId(id)
        );

        return ResponseEntity.ok(respuesta);
    }

    @PostMapping
    public ResponseEntity<SocioResponse> crear(
            @Valid @RequestBody SocioRequest request) {

        SocioResponse respuesta = SocioResponse.desde(
                socioService.crear(request)
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(respuesta);
    }
}
