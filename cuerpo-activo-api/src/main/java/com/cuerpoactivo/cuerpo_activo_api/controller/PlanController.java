package com.cuerpoactivo.cuerpo_activo_api.controller;

import com.cuerpoactivo.cuerpo_activo_api.model.Plan;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/planes")
public class PlanController {

    private List<Plan> planes = new ArrayList<>();
    private Long siguienteId = 4L;

    public PlanController() {
        planes.add(new Plan(1L, "Plan mensual", 1, 120000));
        planes.add(new Plan(2L, "Plan trimestral", 3, 330000));
        planes.add(new Plan(3L, "Plan anual", 12, 1200000));
    }

    // Listar todos los planes
    @GetMapping
    public List<Plan> listarPlanes() {
        return planes;
    }

    // Buscar un plan por ID
    @GetMapping("/{id}")
    public Plan buscarPorId(@PathVariable Long id) {

        for (Plan plan : planes) {
            if (plan.getId().equals(id)) {
                return plan;
            }
        }

        return null;
    }

    // Crear un nuevo plan
    @PostMapping
    public Plan crearPlan(@RequestBody Plan nuevoPlan) {

        nuevoPlan.setId(siguienteId);
        siguienteId++;

        planes.add(nuevoPlan);

        return nuevoPlan;
    }
}