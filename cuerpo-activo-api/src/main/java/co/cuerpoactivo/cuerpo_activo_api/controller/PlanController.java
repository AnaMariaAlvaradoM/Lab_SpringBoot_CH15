package co.cuerpoactivo.cuerpo_activo_api.controller;

import co.cuerpoactivo.cuerpo_activo_api.model.Plan;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/planes")
public class PlanController {

    private final List<Plan> plans = new ArrayList<>();

    public PlanController() {
        plans.add(new Plan(1L, "Plan mensual", 1, 120000));
        plans.add(new Plan(1L, "Plan trimestral", 3, 330000));
        plans.add(new Plan(1L, "Plan anual", 12, 1200000));
    }

    @GetMapping
    public List<Plan> lista() {
        return plans;
    }

    @GetMapping("/{id}")
    public Plan buscarporId(@PathVariable Long id) {
        for (Plan plan : plans) {
            if (plan.getId().equals(id)){
                return plan;
            }
        }
        return null;
    }

    @PostMapping
    public Plan crear(@RequestBody Plan nuevo) {
        nuevo.setId(plans.size() + 1L);
        plans.add(nuevo);
        return nuevo;
    }
}
