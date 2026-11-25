package com.cuerpoactivo.cuerpo_activo_api.controller;

import com.cuerpoactivo.cuerpo_activo_api.model.Plan;
import com.cuerpoactivo.cuerpo_activo_api.service.PlanService;
import org.springframework.web.bind.annotation.*;

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
    public Plan buscarPorId(@PathVariable Long id) {
        return planService.buscarPorId(id).orElse(null);
    }

    // Crear un nuevo plan
    @PostMapping
    public Plan crearPlan(@RequestBody Plan nuevoPlan) {
        return planService.crear(nuevoPlan);
    }
}