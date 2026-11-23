package co.cuerpoactivo.cuerpo_activo_api.controller;


import co.cuerpoactivo.cuerpo_activo_api.model.Plan;
import co.cuerpoactivo.cuerpo_activo_api.service.PlanService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/planes")
public class PlanController {

    private PlanService planService;

    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    @GetMapping
    public List<Plan> listarPlanes() {
        return planService.listar();
    }

    @GetMapping("/{id}")
    public Plan buscarPorId(@PathVariable Long id) {
        return planService.buscarPorId(id).orElse(null);
    }

    @PostMapping
    public Plan guardarPlan(@RequestBody Plan plan) {
        return planService.guardar(plan);
    }

}
