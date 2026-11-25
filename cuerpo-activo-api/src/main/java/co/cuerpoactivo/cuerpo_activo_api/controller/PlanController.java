package co.cuerpoactivo.cuerpo_activo_api.controller;

import co.cuerpoactivo.cuerpo_activo_api.model.Plan;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/planes")
public class PlanController {

    private final List<Plan> planes = new ArrayList<>(List.of(
            new Plan(1L, "Plan mensual", 1, 120000),
            new Plan(2L, "Plan trimestral", 3, 330000),
            new Plan(3L, "Plan anual", 12, 1200000)
    ));
    private Long siguienteId = 4L;

    @GetMapping
    public List<Plan> listar() {
        return planes;
    }

    @GetMapping("/{id}")
    public Plan buscarPorId(@PathVariable Long id) {
        return planes.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @PostMapping
    public Plan crear(@RequestBody Plan nuevo) {
        nuevo.setId(siguienteId++);
        planes.add(nuevo);
        return nuevo;
    }
}
