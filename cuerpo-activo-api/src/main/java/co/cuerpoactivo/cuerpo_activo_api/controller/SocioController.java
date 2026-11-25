
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

    // GET: consultar todos los socios
    @GetMapping
    public List<SocioResponse> listar() {

        return socioService.listar()
                .stream()
                .map(SocioResponse::desde)
                .toList();
    }

    // GET: consultar por ID
    @GetMapping("/{id}")
    public ResponseEntity<SocioResponse> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                SocioResponse.desde(socioService.buscarPorId(id))
        );
    }

    // POST: crear socio
    @PostMapping
    public ResponseEntity<SocioResponse> crear(
            @Valid @RequestBody SocioRequest request) {

        var creado = socioService.crear(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(SocioResponse.desde(creado));
    }
}


