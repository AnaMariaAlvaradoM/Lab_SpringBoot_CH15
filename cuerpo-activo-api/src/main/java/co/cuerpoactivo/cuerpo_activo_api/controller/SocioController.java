package co.cuerpoactivo.cuerpo_activo_api.controller;

import co.cuerpoactivo.cuerpo_activo_api.dto.SocioRequest;
import co.cuerpoactivo.cuerpo_activo_api.dto.SocioResponse;
import co.cuerpoactivo.cuerpo_activo_api.model.Socio;
import co.cuerpoactivo.cuerpo_activo_api.service.PlanService;
import co.cuerpoactivo.cuerpo_activo_api.service.SocioService;
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
    private final PlanService planService;

    public SocioController(SocioService socioService, PlanService planService) {
        this.socioService = socioService;
        this.planService = planService;
    }

    @GetMapping
    public List<SocioResponse> listar() {
        return socioService.buscarTodos().stream()
                .map(this::aRespuesta)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SocioResponse> buscarPorId(@PathVariable Long id) {
        return socioService.buscarPorId(id)
                .map(socio -> ResponseEntity.ok(aRespuesta(socio)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SocioResponse> crear(@RequestBody SocioRequest request) {
        if (request.planId() == null) {
            return ResponseEntity.badRequest().build();
        }
        return planService.buscarPorId(request.planId())
                .map(plan -> {
                    Socio socio = new Socio();
                    socio.setNombre(request.nombre());
                    socio.setPlan(plan);
                    Socio guardado = socioService.guardar(socio);
                    return ResponseEntity.status(HttpStatus.CREATED).body(aRespuesta(guardado));
                })
                .orElse(ResponseEntity.badRequest().build());
    }

    private SocioResponse aRespuesta(Socio socio) {
        String nombrePlan = socio.getPlan() != null ? socio.getPlan().getNombre() : null;
        return new SocioResponse(socio.getId(), socio.getNombre(), nombrePlan);
    }
}