package com.cuerpoactivo.cuerpo_activo_api.controller;

import com.cuerpoactivo.cuerpo_activo_api.dto.PlanRequest;
import com.cuerpoactivo.cuerpo_activo_api.dto.PlanResponse;
import com.cuerpoactivo.cuerpo_activo_api.services.PlanService;
import jakarta.validation.Valid;
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
        return planService.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanResponse> buscarPorId(@PathVariable Long id) {
        Optional<PlanResponse> plan = planService.buscarPorId(id);
        if (plan.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(plan.get());
    }

    @PostMapping
    public ResponseEntity<PlanResponse> crear(@Valid @RequestBody PlanRequest datos) {
        PlanResponse creado = planService.crear(datos);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlanResponse> actualizar(@PathVariable Long id, @Valid @RequestBody PlanRequest datos) {
        Optional<PlanResponse> actualizado = planService.actualizar(id, datos);
        if (actualizado.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(actualizado.get());
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