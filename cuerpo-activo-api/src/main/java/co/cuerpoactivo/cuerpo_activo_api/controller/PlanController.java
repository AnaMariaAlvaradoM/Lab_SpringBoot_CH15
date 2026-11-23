package co.cuerpoactivo.cuerpo_activo_api.controller;

import co.cuerpoactivo.cuerpo_activo_api.model.Plan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/planes")
public class PlanController {

    private final List<Plan> planes = new ArrayList<>();
    private final AtomicLong siguienteId = new AtomicLong(4);

    public PlanController() {
        planes.add(new Plan(1L, "Plan mensual", 1, 120000));
        planes.add(new Plan(2L, "Plan trimestral", 3, 330000));
        planes.add(new Plan(3L, "Plan anual", 12, 1200000));
    }

    @GetMapping
    public List<Plan> listarPlanes() {
        return planes;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Plan> buscarPorId(@PathVariable Long id) {
        for (Plan plan : planes) {
            if (plan.getId().equals(id)) {
                return ResponseEntity.ok(plan);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Plan> crearPlan(@RequestBody Plan nuevoPlan) {
        nuevoPlan.setId(siguienteId.getAndIncrement());
        planes.add(nuevoPlan);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPlan);
    }
}