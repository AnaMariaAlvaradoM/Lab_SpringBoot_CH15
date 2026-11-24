package com.cuerpoactivo.cuerpo_activo_api.controller;

import com.cuerpoactivo.cuerpo_activo_api.model.Plan;
import com.cuerpoactivo.cuerpo_activo_api.service.PlanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/planes")
public class PlanController {

    private final PlanService planService;

    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    @GetMapping
    public List<Plan> obtenerTodos() {
        return planService.listarPlanes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Plan> obtenerPorId(@PathVariable Long id) {
        return planService.obtenerPlanPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Plan> crearPlan(@RequestBody Plan nuevoPlan) {
        Plan creado = planService.crearPlan(nuevoPlan);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }
}

