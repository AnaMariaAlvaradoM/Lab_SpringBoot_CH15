package co.cuerpoactivo.cuerpo_activo_api.controller;
import co.cuerpoactivo.cuerpo_activo_api.model.Plan;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import co.cuerpoactivo.cuerpo_activo_api.service.PlanService;

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
    public Plan obtenerPorId(@PathVariable Long id) {
        return planService.buscarPorId(id).orElse(null);
    }

    @PostMapping
    public Plan crearPlan(@RequestBody Plan plan) {
        return planService.guardar(plan);
    }
}