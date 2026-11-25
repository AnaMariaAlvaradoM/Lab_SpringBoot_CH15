package co.cuerpoactivo.cuerpo_activo_api.controller;

import co.cuerpoactivo.cuerpo_activo_api.dto.SocioRequest;
import co.cuerpoactivo.cuerpo_activo_api.dto.SocioResponse;
import co.cuerpoactivo.cuerpo_activo_api.model.Socio;
import co.cuerpoactivo.cuerpo_activo_api.service.SocioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/socios")
public class SocioController {

    private final SocioService socioService;

    public SocioController(SocioService socioService) {
        this.socioService = socioService;
    }

    @GetMapping
    public ResponseEntity<List<SocioResponse>> listar() {
        List<Socio> socios = socioService.listar();
        List<SocioResponse> respuesta = new ArrayList<>();
        for (Socio socio : socios) {
            respuesta.add(SocioResponse.desde(socio));
        }
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SocioResponse> buscarPorId(@PathVariable Long id) {
        Optional<Socio> socioOptional = socioService.buscarPorId(id);
        if (socioOptional.isPresent()) {
            return ResponseEntity.ok(SocioResponse.desde(socioOptional.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<SocioResponse> crear(@RequestBody SocioRequest request) {
        Optional<Socio> crearOptional = socioService.crear(request);
        if (crearOptional.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(SocioResponse.desde(crearOptional.get()));
    }
}
