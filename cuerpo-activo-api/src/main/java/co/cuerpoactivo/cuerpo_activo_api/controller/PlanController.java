package co.cuerpoactivo.cuerpo_activo_api.controller;
import co.cuerpoactivo.cuerpo_activo_api.model.Plan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

import co.cuerpoactivo.cuerpo_activo_api.service.PlanService;

@RestController
@RequestMapping("/api/planes")
public class PlanController {

    private final PlanService planService;

    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    @GetMapping
    public List<Plan> listarPlanes() {
        return planService.buscarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Plan> obtenerPorId(@PathVariable Long id) {
        Optional<Plan> plan = planService.buscarPorId(id);

        if (plan.isPresent()) {
            return ResponseEntity.ok(plan.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Plan> crearPlan(@RequestBody Plan plan) {
        Plan guardado = planService.guardar(plan);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Plan> actualizarPlan(@PathVariable Long id, @RequestBody Plan cambios) {
        Optional<Plan> actualizado = planService.actualizar(id, cambios);

        if (actualizado.isPresent()) {
            return ResponseEntity.ok(actualizado.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrarPlan(@PathVariable Long id){
        boolean borrar = planService.eliminar(id);

        if(borrar){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    }

