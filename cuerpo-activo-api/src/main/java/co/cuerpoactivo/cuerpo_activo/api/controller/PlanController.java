
package co.cuerpoactivo.cuerpo_activo.api.controller;

import co.cuerpoactivo.cuerpo_activo.api.dto.PlanRequest;
import co.cuerpoactivo.cuerpo_activo.api.dto.PlanResponse;
import co.cuerpoactivo.cuerpo_activo.api.model.Plan;
import co.cuerpoactivo.cuerpo_activo.api.service.PlanService;
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

    // GET: listar todos los planes
    @GetMapping
    public List<PlanResponse> listarPlanes() {
        return planService.buscarTodos()
                .stream()
                .map(PlanResponse::desde)
                .toList();
    }

    // GET: buscar un plan por ID
    @GetMapping("/{id}")
    public ResponseEntity<PlanResponse> buscarPorId(@PathVariable Long id) {
        Plan plan = planService.buscarPorId(id);
        return ResponseEntity.ok(PlanResponse.desde(plan));
    }

    // POST: crear un plan
    @PostMapping
    public ResponseEntity<PlanResponse> crear(
            @Valid @RequestBody PlanRequest request) {

        Plan planGuardado = planService.crear(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(PlanResponse.desde(planGuardado));
    }

    // PUT: actualizar un plan
    @PutMapping("/{id}")
    public ResponseEntity<PlanResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody PlanRequest request) {

        Plan planActualizado = planService.actualizar(id, request);

        return ResponseEntity.ok(PlanResponse.desde(planActualizado));
    }

    // DELETE: eliminar un plan
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {

        planService.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}
