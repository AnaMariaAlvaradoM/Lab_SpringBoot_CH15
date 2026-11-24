package co.cuerpoactivo.cuerpo_activo_api.controller;

import co.cuerpoactivo.cuerpo_activo_api.model.Plan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/planes")
public class PlanController {

    private final List<Plan> planes = new ArrayList<>();
    private Long siguienteId = 4L;

    public PlanController() {
        planes.add(new Plan(1L, "Plan mensual", 1, 120000));
        planes.add(new Plan(2L, "Plan trimestral", 3, 330000));
        planes.add(new Plan(3L, "Plan anual", 12, 1200000));
    }

    @GetMapping
    public List<Plan> listar() {
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
    public Plan crear(@RequestBody Plan nuevo) {
        nuevo.setId(siguienteId);
        siguienteId++;
        planes.add(nuevo);
        return nuevo;
    }
}
