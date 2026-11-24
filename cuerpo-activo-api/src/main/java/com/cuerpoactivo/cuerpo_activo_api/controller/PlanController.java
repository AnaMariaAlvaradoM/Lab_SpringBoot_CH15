package com.cuerpoactivo.cuerpo_activo_api.controller;

import com.cuerpoactivo.cuerpo_activo_api.model.Plan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cuerpoactivo.cuerpo_activo_api.services.PlanService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/planes")
public class PlanController {
    private final PlanService planService;

    public PlanController(PlanService planservice) {
        this.planService = planservice;
    }

    @GetMapping
    public List<Plan> listar() {
        return planService.listar();
    }

    @GetMapping("/{id}")
    public Plan buscarPorId(@PathVariable Long id) {
        return planService.buscarPorId(id).orElse(null);
    }

    @PostMapping
    public Plan crear(@RequestBody Plan nuevo) {
        return planService.crear(nuevo);
    }
}
