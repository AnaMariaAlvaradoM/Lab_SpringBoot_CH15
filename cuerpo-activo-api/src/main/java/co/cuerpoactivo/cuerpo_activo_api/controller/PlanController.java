
package co.cuerpoactivo.cuerpo_activo_api.controller;

import co.cuerpoactivo.cuerpo_activo_api.dto.PlanRequest;
import co.cuerpoactivo.cuerpo_activo_api.dto.PlanResponse;
import co.cuerpoactivo.cuerpo_activo_api.service.PlanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/planes")
public class PlanController {

    private final PlanService planService;

    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    @GetMapping
    public List<PlanResponse> listar() {
        return planService.listar()
                .stream()
                .map(PlanResponse::desde)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanResponse> buscarPorId(@PathVariable Long id) {
        return planService.buscarPorId(id)
                .map(PlanResponse::desde)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PlanResponse> crear(
            @RequestBody PlanRequest request) {

        PlanResponse respuesta = PlanResponse.desde(
                planService.crear(request)
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlanResponse> actualizar(
            @PathVariable Long id,
            @RequestBody PlanRequest request) {

        return planService.actualizar(id, request)
                .map(PlanResponse::desde)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        boolean eliminado = planService.eliminar(id);

        if (!eliminado) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}
