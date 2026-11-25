package co.cuerpoactivo.cuerpo_activo.api.controller;

import co.cuerpoactivo.cuerpo_activo.api.dto.SocioRequest;
import co.cuerpoactivo.cuerpo_activo.api.dto.SocioResponse;
import co.cuerpoactivo.cuerpo_activo.api.model.Socio;
import co.cuerpoactivo.cuerpo_activo.api.service.SocioService;
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

    // GET: buscar un socio por ID
    @GetMapping("/{id}")
    public ResponseEntity<SocioResponse> buscarPorId(@PathVariable Long id) {
        Socio socio = socioService.buscarPorId(id);
        return ResponseEntity.ok(SocioResponse.desde(socio));

    }

    @PostMapping
    public ResponseEntity<SocioResponse> crear(
            @Valid @RequestBody SocioRequest request) {

        Socio socioCreado = socioService.crear(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(SocioResponse.desde(socioCreado));
    }
}
