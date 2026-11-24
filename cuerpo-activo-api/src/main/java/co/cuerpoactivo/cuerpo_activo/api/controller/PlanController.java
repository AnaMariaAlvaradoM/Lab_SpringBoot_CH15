package co.cuerpoactivo.cuerpo_activo.api.controller;

import co.cuerpoactivo.cuerpo_activo.api.model.Plan;
import co.cuerpoactivo.cuerpo_activo.api.service.PlanService;
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
    public List<Plan> listarPlanes() {
        return planService.buscarTodos();
    }

    @GetMapping("/{id}")
    public Optional<Plan> buscarPorId(@PathVariable Long id) {
        return planService.buscarPorId(id);
    }

    @PostMapping
    public Plan crear(@RequestBody Plan nuevoPlan) {
        return planService.guardar(nuevoPlan);
    }
}