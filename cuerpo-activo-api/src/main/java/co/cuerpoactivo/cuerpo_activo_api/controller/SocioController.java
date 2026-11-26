
package co.cuerpoactivo.cuerpo_activo_api.controller;

import co.cuerpoactivo.cuerpo_activo_api.dto.SocioRequest;
import co.cuerpoactivo.cuerpo_activo_api.dto.SocioResponse;
import co.cuerpoactivo.cuerpo_activo_api.model.Socio;
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
    public ResponseEntity<List<SocioResponse>> listar() {
        List<SocioResponse> socios = socioService.listar()
                .stream()
                .map(SocioResponse::desde)
                .toList();

        return ResponseEntity.ok(socios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SocioResponse> buscarPorId(@PathVariable Long id) {
        Socio socio = socioService.buscarPorId(id);

        return ResponseEntity.ok(SocioResponse.desde(socio));
    }

    @PostMapping
    public ResponseEntity<SocioResponse> crear(
            @Valid @RequestBody SocioRequest solicitud) {

        Socio socio = socioService.crear(solicitud);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(SocioResponse.desde(socio));
    }
}
