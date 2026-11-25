package co.cuerpoactivo.cuerpo_activo_api.controller;

import co.cuerpoactivo.cuerpo_activo_api.dto.PlanRequest;
import co.cuerpoactivo.cuerpo_activo_api.dto.PlanResponse;
import co.cuerpoactivo.cuerpo_activo_api.model.Plan;
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
    public ResponseEntity<List<PlanResponse>> obtenerPlanes() {
        List<PlanResponse> planes = planService.buscarTodos()
                .stream()
                .map(PlanResponse::desde)
                .toList();

        return ResponseEntity.ok(planes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanResponse> obtenerPlanPorId(@PathVariable Long id) {
        Optional<Plan> plan = planService.buscarPorId(id);

        return plan.map(PlanResponse::desde)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PlanResponse> crearPlan(@RequestBody PlanRequest nuevoPlan) {
        Plan planGuardado = planService.guardar(nuevoPlan);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(PlanResponse.desde(planGuardado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlanResponse> actualizarPlan(
            @PathVariable Long id,
            @RequestBody PlanRequest datosPlan) {

        Optional<Plan> planActualizado = planService.actualizar(id, datosPlan);

        return planActualizado.map(PlanResponse::desde)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPlan(@PathVariable Long id) {
        boolean eliminado = planService.eliminar(id);

        if (!eliminado) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}