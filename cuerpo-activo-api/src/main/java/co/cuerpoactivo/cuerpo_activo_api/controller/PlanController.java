
package co.cuerpoactivo.cuerpo_activo_api.controller;

import co.cuerpoactivo.cuerpo_activo_api.model.Plan;
import co.cuerpoactivo.cuerpo_activo_api.service.PlanService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public List<Plan> obtenerPlanes() {
        return planService.buscarTodos();
    }

    @GetMapping("/{id}")
    public Optional<Plan> obtenerPlanPorId(@PathVariable Long id) {
        return planService.buscarPorId(id);
    }

    @PostMapping
    public Plan crearPlan(@RequestBody Plan nuevoPlan) {
        return planService.guardar(nuevoPlan);
    }
}
