package co.cuerpoactivo.cuerpo_activo_api.controller;

import co.cuerpoactivo.cuerpo_activo_api.model.Plan;
import co.cuerpoactivo.cuerpo_activo_api.service.PlanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/planes")
public class PlanController {

    private final PlanService planService;

    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    @GetMapping
    public List<Plan> listar() {
        return planService.buscarTodos(); // La lista no cambia
    }

    @GetMapping("/{id}")
    public ResponseEntity<Plan> buscarPorId(@PathVariable Long id) {
        Plan plan = planService.buscarPorId(id);
        if (plan != null) {
            return ResponseEntity.ok(plan); // 200
        }
        return ResponseEntity.notFound().build(); // 404
    }

    @PostMapping
    public ResponseEntity<Plan> crear(@RequestBody Plan nuevo) {
        Plan creado = planService.guardar(nuevo);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado); // 201
    }

    @PutMapping("/{id}")
    public ResponseEntity<Plan> actualizar(@PathVariable Long id, @RequestBody Plan cambios) {
        return planService.actualizar(id, cambios)
                .map(ResponseEntity::ok) // Si existe, devuelve 200 con el plan
                .orElseGet(() -> ResponseEntity.notFound().build()); // Si no, 404
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (planService.eliminar(id)) {
            return ResponseEntity.noContent().build(); // 204
        }
        return ResponseEntity.notFound().build(); // 404
    }
}