package co.cuerpoactivo.cuerpo_activo_api.controller;

import co.cuerpoactivo.cuerpo_activo_api.dto.SocioRequest;
import co.cuerpoactivo.cuerpo_activo_api.dto.SocioResponse;
import co.cuerpoactivo.cuerpo_activo_api.model.Socio;
import co.cuerpoactivo.cuerpo_activo_api.service.SocioService;
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
        return socioService.buscarTodos().stream()
                .map(this::aRespuesta)
                .toList();
    }

    @GetMapping("/{id}")
    public SocioResponse buscarPorId(@PathVariable Long id) {
        return aRespuesta(socioService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<SocioResponse> crear(@Valid @RequestBody SocioRequest request) {
        Socio guardado = socioService.inscribir(request.nombre(), request.planId());
        return ResponseEntity.status(HttpStatus.CREATED).body(aRespuesta(guardado));
    }

    private SocioResponse aRespuesta(Socio socio) {
        String nombrePlan = socio.getPlan() != null ? socio.getPlan().getNombre() : null;
        return new SocioResponse(socio.getId(), socio.getNombre(), nombrePlan);
    }
}