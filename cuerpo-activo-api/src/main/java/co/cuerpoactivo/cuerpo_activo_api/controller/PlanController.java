
package co.cuerpoactivo.cuerpo_activo_api.controller;

import co.cuerpoactivo.cuerpo_activo_api.dto.PlanRequest;
import co.cuerpoactivo.cuerpo_activo_api.dto.PlanResponse;
import co.cuerpoactivo.cuerpo_activo_api.model.Plan;
import co.cuerpoactivo.cuerpo_activo_api.service.PlanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/planes")
public class PlanController {

    private PlanService planService;

    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    @GetMapping
    public List<PlanResponse> listarPlanes() {
        List<PlanResponse> respuesta = new ArrayList<>();
        for (Plan plan : planService.listar()) {
            respuesta.add(PlanResponse.desde(plan));
        }
        return respuesta;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(
                PlanResponse.desde(planService.buscarPorId(id))
        );
    }

    @PostMapping
    public ResponseEntity<PlanResponse> guardarPlan(@Valid @RequestBody PlanRequest request) {
        Plan guardado = planService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(PlanResponse.desde(guardado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlanResponse> actualizar(@PathVariable Long id,@Valid @RequestBody PlanRequest request) {
        return ResponseEntity.ok(
                PlanResponse.desde(planService.actualizar(id, request))
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        planService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
