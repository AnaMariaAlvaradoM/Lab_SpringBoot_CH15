package co.cuerpoactivo.cuerpo_activo_api.controller;

import co.cuerpoactivo.cuerpo_activo_api.model.Plan;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/planes")
public class PlanController {

    private List<Plan> planes = new ArrayList<>();

    public PlanController() {
        planes.add(new Plan(1L, "Plan mensual", 1, 120000));
        planes.add(new Plan(2L, "Plan trimestral", 3, 330000));
        planes.add(new Plan(3L, "Plan anual", 12, 1200000));
    }

    @GetMapping
    public List<Plan> obtenerTodos() {
        return planes;
    }

    @GetMapping("/{id}")
    public Plan obtenerPorId(@PathVariable Long id) {

        for (Plan plan : planes) {
            if (plan.getId().equals(id)) {
                return plan;
            }
        }

        return null;
    }

    @PostMapping
    public Plan guardar(@RequestBody Plan nuevoPlan) {

        Long nuevoId = (long) (planes.size() + 1);

        nuevoPlan.setId(nuevoId);

        planes.add(nuevoPlan);

        return nuevoPlan;
    }
}