package co.cuerpoactivo.cuerpo_activo_api.controller;

import co.cuerpoactivo.cuerpo_activo_api.dto.PlanRequest;
import co.cuerpoactivo.cuerpo_activo_api.dto.PlanResponse;
import co.cuerpoactivo.cuerpo_activo_api.model.Plan;
import co.cuerpoactivo.cuerpo_activo_api.service.PlanService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/planes")
public class PlanController {

    private final PlanService planService;

    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    @GetMapping
    public List<PlanResponse> listarPlanes() {
        return planService.buscarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanResponse> obtenerPlanPorId(@PathVariable Long id) {
        Plan plan = planService.buscarPorId(id);
        return ResponseEntity.ok(PlanResponse.desde(plan));
    }

    @PostMapping
    public ResponseEntity<PlanResponse> crearPlan(@Valid @RequestBody PlanRequest request) {
        PlanResponse creado = planService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlanResponse> actualizarPlan(@PathVariable Long id, @Valid @RequestBody PlanRequest cambios) {
        Plan actualizado = planService.actualizar(id, cambios);
        return ResponseEntity.ok(PlanResponse.desde(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPlan(@PathVariable Long id) {
        planService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}