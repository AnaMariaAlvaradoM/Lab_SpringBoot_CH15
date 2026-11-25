package com.cuerpoactivo.cuerpo_activo_api.controller;

import com.cuerpoactivo.cuerpo_activo_api.dto.PlanRequest;
import com.cuerpoactivo.cuerpo_activo_api.dto.PlanResponse;
import com.cuerpoactivo.cuerpo_activo_api.service.PlanService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

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
        return planService.listar()
                .stream()
                .map(PlanResponse::desde)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(
                PlanResponse.desde(planService.buscarPorId(id)));
    }

    @PostMapping
    public ResponseEntity<PlanResponse> crearPlan(
            @Valid @RequestBody PlanRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(PlanResponse.desde(planService.crear(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlanResponse> actualizarPlan(
            @PathVariable Long id,
            @Valid @RequestBody PlanRequest request) {

        return ResponseEntity.ok(
                PlanResponse.desde(planService.actualizar(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPlan(@PathVariable Long id) {
        planService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}