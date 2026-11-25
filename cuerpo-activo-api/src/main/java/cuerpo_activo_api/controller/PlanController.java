package cuerpo_activo_api.controller;

import cuerpo_activo_api.dto.PlanRequest;
import cuerpo_activo_api.dto.PlanResponse;
import cuerpo_activo_api.service.PlanService;
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
    public List<PlanResponse> listar() {
        return planService.buscarTodos().stream().map(PlanResponse::desde).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanResponse> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(PlanResponse.desde(planService.buscarPorId(id)));
    }

    @PostMapping
    public ResponseEntity<PlanResponse> crear(@Valid @RequestBody PlanRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(PlanResponse.desde(planService.crear(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlanResponse> actualizar(@PathVariable Long id, @Valid @RequestBody PlanRequest request) {
        return ResponseEntity.ok(PlanResponse.desde(planService.actualizar(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        planService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}