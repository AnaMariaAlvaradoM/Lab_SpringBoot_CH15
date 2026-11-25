
package co.cuerpoactivo.cuerpo_activo_api.controller;

import co.cuerpoactivo.cuerpo_activo_api.dto.PlanRequest;
import co.cuerpoactivo.cuerpo_activo_api.dto.PlanResponse;
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

    // GET: consultar todos
    @GetMapping
    public List<PlanResponse> obtenerPlanes() {

        return planService.buscarTodos()
                .stream()
                .map(PlanResponse::desde)
                .toList();
    }

    // GET: consultar por ID
    @GetMapping("/{id}")
    public ResponseEntity<PlanResponse> obtenerPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                PlanResponse.desde(planService.buscarPorId(id))
        );
    }

    // POST: crear
    @PostMapping
    public ResponseEntity<PlanResponse> crearPlan(
            @Valid @RequestBody PlanRequest request) {

        var creado = planService.crear(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(PlanResponse.desde(creado));
    }

    // PUT: actualizar
    @PutMapping("/{id}")
    public ResponseEntity<PlanResponse> actualizarPlan(
            @PathVariable Long id,
            @Valid @RequestBody PlanRequest request) {

        var actualizado = planService.actualizar(id, request);

        return ResponseEntity.ok(
                PlanResponse.desde(actualizado)
        );
    }

    // DELETE: eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPlan(
            @PathVariable Long id) {

        planService.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}



