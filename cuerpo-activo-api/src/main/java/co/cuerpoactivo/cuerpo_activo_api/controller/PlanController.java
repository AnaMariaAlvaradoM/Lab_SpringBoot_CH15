package co.cuerpoactivo.cuerpo_activo_api.controller;

import co.cuerpoactivo.cuerpo_activo_api.model.Plan;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/planes")

public class PlanController {

    private final List<Plan> planes = new ArrayList<>();
    private Long siguienteId = 4L;

    public PlanController() {
        planes.add(new Plan(1L, "Plan Mensual", 1, 120000));
        planes.add(new Plan(2L, "Plan trimestral", 3, 330000));
        planes.add(new Plan(3L, "Plan Anual", 12, 1200000));
    }

    @GetMapping
    public List<Plan> ListarPlanes(){
        return planes;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Plan> obtenerPorId(@PathVariable Long id) {
        for (Plan plan : planes) {
            if (Objects.equals(plan.getId(), id)) {
                return ResponseEntity.ok(plan);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Plan> crearPlan(@RequestBody Plan planRecibido) {
        Plan nuevoPlan = new Plan(
                siguienteId,
                planRecibido.getNombre(),
                planRecibido.getDuracionMeses(),
                planRecibido.getPrecio()
        );
        siguienteId++;
        planes.add(nuevoPlan);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPlan);
    }
}
