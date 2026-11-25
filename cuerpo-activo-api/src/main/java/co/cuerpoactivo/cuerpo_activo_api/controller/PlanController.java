package co.cuerpoactivo.cuerpo_activo_api.controller;

import co.cuerpoactivo.cuerpo_activo_api.dto.PlanRequest;
import co.cuerpoactivo.cuerpo_activo_api.dto.PlanResponse;
import co.cuerpoactivo.cuerpo_activo_api.model.Plan;
import co.cuerpoactivo.cuerpo_activo_api.service.PlanService;
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
        return planService.listar().stream()
                .map(PlanResponse::desde)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanResponse> buscarPorId(@PathVariable Long id) {
        return planService.buscarPorId(id)
                .map(plan -> ResponseEntity.ok(PlanResponse.desde(plan)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PlanResponse> crear(@RequestBody PlanRequest nuevo) {
        Plan creado = planService.crear(nuevo);
        return ResponseEntity.status(HttpStatus.CREATED).body(PlanResponse.desde(creado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlanResponse> actualizar(@PathVariable Long id, @RequestBody PlanRequest cambios) {
        return planService.actualizar(id, cambios)
                .map(plan -> ResponseEntity.ok(PlanResponse.desde(plan)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!planService.eliminar(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}