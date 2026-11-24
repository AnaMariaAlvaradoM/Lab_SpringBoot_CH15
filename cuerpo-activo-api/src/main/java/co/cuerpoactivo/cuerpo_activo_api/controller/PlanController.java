
package co.cuerpoactivo.cuerpo_activo_api.controller;

import co.cuerpoactivo.cuerpo_activo_api.model.Plan;
import co.cuerpoactivo.cuerpo_activo_api.service.PlanService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/planes")
public class PlanController {

    private final PlanService planService;

    // Inyección de dependencias por constructor
    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    // GET: obtener todos los planes
    @GetMapping
    public List<Plan> obtenerPlanes() {
        return planService.buscarTodos();
    }

    // GET: obtener plan por ID
    @GetMapping("/{id}")
    public ResponseEntity<Plan> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.of(planService.buscarPorId(id));
    }

    // POST: guardar un plan
    @PostMapping
    public Plan crearPlan(@RequestBody Plan nuevoPlan) {
        return planService.guardar(nuevoPlan);
    }
}


