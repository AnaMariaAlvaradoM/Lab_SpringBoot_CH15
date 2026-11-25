package co.cuerpoactivo.cuerpo_activo_api.controller;

import co.cuerpoactivo.cuerpo_activo_api.dto.SocioRequest;
import co.cuerpoactivo.cuerpo_activo_api.dto.SocioResponse;
import co.cuerpoactivo.cuerpo_activo_api.service.SocioService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/socios")
public class SocioController {

    private final SocioService socioService;

    public SocioController(SocioService socioService) {
        this.socioService = socioService;
    }

    @GetMapping
    public ResponseEntity<List<SocioResponse>> listar() {
        List<SocioResponse> socios = socioService.listar().stream().map(SocioResponse::desde).toList();
        return ResponseEntity.ok(socios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SocioResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(SocioResponse.desde(socioService.buscarPorId(id)));
    }

    @PostMapping
    public ResponseEntity<SocioResponse> crear(@Valid @RequestBody SocioRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(SocioResponse.desde(socioService.crear(request)));
    }
}
