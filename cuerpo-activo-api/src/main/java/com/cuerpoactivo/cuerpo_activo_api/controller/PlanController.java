package com.cuerpoactivo.cuerpo_activo_api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cuerpoactivo.cuerpo_activo_api.dto.PlanRequest;
import com.cuerpoactivo.cuerpo_activo_api.dto.PlanResponse;
import com.cuerpoactivo.cuerpo_activo_api.model.Plan;
import com.cuerpoactivo.cuerpo_activo_api.service.PlanService;

@RestController
@RequestMapping("/api/planes")
public class PlanController {

    private final PlanService planService;

    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    @GetMapping
    public List<PlanResponse> listar() {
        return planService.buscarTodos().stream()
                .map(PlanResponse::desde)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanResponse> buscar(@PathVariable Long id) {
        Optional<Plan> plan = planService.buscarPorId(id);
        if (plan.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(PlanResponse.desde(plan.get()));
    }

    @PostMapping
    public ResponseEntity<PlanResponse> crear(@RequestBody PlanRequest datos) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(PlanResponse.desde(planService.crear(datos)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlanResponse> actualizar(@PathVariable Long id, @RequestBody PlanRequest cambios) {
        Optional<Plan> actualizado = planService.actualizar(id, cambios);
        if (actualizado.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(PlanResponse.desde(actualizado.get()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!planService.eliminar(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
