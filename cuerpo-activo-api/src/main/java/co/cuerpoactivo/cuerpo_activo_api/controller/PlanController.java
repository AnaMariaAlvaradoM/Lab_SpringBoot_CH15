
package co.cuerpoactivo.cuerpo_activo_api.controller;

import co.cuerpoactivo.cuerpo_activo_api.model.Plan;
import org.springframework.web.bind.annotation.*;

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

    // Consultar todos los planes
    @GetMapping
    public List<Plan> listar() {
        return planes;
    }

    // Consultar un plan por su ID
    @GetMapping("/{id}")
    public Plan buscarPorId(@PathVariable Long id) {
        return planes.stream()
                .filter(plan -> plan.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // Registrar un nuevo plan
    @PostMapping
    public Plan crear(@RequestBody Plan plan) {
        plan.setId(siguienteId++);
        planes.add(plan);
        return plan;
    }
}
