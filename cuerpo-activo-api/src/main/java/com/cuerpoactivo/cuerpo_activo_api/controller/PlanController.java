package com.cuerpoactivo.cuerpo_activo_api.controller;

import com.cuerpoactivo.cuerpo_activo_api.model.Plan;
import com.cuerpoactivo.cuerpo_activo_api.service.PlanService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/api/planes")
public class PlanController {

    private final PlanService planService;

    // Inyección de dependencias por constructor
    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    // Listar todos los planes
    @GetMapping
    public List<Plan> listarPlanes() {
        return planService.listar();
    }

    // Buscar un plan por ID
    @GetMapping("/{id}")
    public ResponseEntity<Plan> buscarPorId(@PathVariable Long id) {
        return planService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear un nuevo plan
    @PostMapping
    public ResponseEntity<Plan> crearPlan(@RequestBody Plan nuevoPlan) {
        Plan creado = planService.crear(nuevoPlan);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    // Actualizar un plan
    @PutMapping("/{id}")
    public ResponseEntity<Plan> actualizarPlan(
            @PathVariable Long id,
            @RequestBody Plan cambios) {

        return planService.actualizar(id, cambios)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Eliminar un plan
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPlan(@PathVariable Long id) {
        if (planService.eliminar(id)) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}