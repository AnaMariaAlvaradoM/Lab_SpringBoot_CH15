
package co.cuerpoactivo.cuerpo_activo_api.controller;

import co.cuerpoactivo.cuerpo_activo_api.model.Plan;
import co.cuerpoactivo.cuerpo_activo_api.service.PlanService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/planes")
public class PlanController {

    private final PlanService planService;

    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    // Consultar todos los planes
    @GetMapping
    public List<Plan> listar() {
        return planService.buscarTodos();
    }

    // Consultar un plan por su ID
    @GetMapping("/{id}")
    public Plan buscarPorId(@PathVariable Long id) {
        return planService.buscarPorId(id).orElse(null);
    }

    // Registrar un nuevo plan
    @PostMapping
    public Plan crear(@RequestBody Plan plan) {
        return planService.guardar(plan);
    }
}
