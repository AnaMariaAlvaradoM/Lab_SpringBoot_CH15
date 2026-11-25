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
    public List<Plan> listarPlanes() {
        return planService.listarPlanes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Plan> buscarPorId(@PathVariable Long id) {
        return planService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Plan> crearPlan(@RequestBody Plan nuevoPlan) {
        Plan creado = planService.guardarPlan(nuevoPlan);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }
}