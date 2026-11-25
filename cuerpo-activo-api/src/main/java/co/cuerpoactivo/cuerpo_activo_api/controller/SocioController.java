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
    public List<SocioResponse> listarSocios() {
        return socioService.listar().stream()
                .map(SocioResponse::desde)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SocioResponse> obtenerSocioPorId(@PathVariable Long id) {
        return socioService.buscarPorId(id)
                .map(SocioResponse::desde)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SocioResponse> crearSocio(@Valid @RequestBody SocioRequest request) {
        return socioService.crear(request)
                .map(SocioResponse::desde)
                .map(respuesta -> ResponseEntity.status(HttpStatus.CREATED).body(respuesta))
                .orElse(ResponseEntity.badRequest().build());
    }
}