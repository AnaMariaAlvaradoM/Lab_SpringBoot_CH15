package co.cuerpoactivo.cuerpo_activo_api.controller;

import co.cuerpoactivo.cuerpo_activo_api.dto.PlanRequest;
import co.cuerpoactivo.cuerpo_activo_api.dto.PlanResponse;
import co.cuerpoactivo.cuerpo_activo_api.model.Plan;
import co.cuerpoactivo.cuerpo_activo_api.service.PlanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/planes")
public class PlanController {

    private final PlanService planService;

    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    @GetMapping
    public ResponseEntity<List<PlanResponse>> listar() {
        List<Plan> planes = planService.listar();
        List<PlanResponse> respuesta = new ArrayList<>();
        for (Plan plan : planes) {
            respuesta.add(PlanResponse.desde(plan));
        }
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanResponse> buscarporId(@PathVariable Long id) {
        Optional<Plan> planOptional = planService.buscarPorId(id);
        if (planOptional.isPresent()) {
            return ResponseEntity.ok(PlanResponse.desde(planOptional.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<PlanResponse> crear(@RequestBody PlanRequest nuevo) {
        Plan creado = planService.crear(nuevo);
        return ResponseEntity.status(HttpStatus.CREATED).body(PlanResponse.desde(creado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlanResponse> actualizar(@PathVariable Long id, @RequestBody PlanRequest datos) {
        Optional<Plan> actualizadorOptional = planService.actualizar(id, datos);
        if (actualizadorOptional.isPresent()) {
            return ResponseEntity.ok(PlanResponse.desde(actualizadorOptional.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        boolean eliminado = planService.eliminar(id);
        if (!eliminado) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
