package co.cuerpoactivo.cuerpo_activo_api.controller;

import co.cuerpoactivo.cuerpo_activo_api.dto.PlanResponse;
import co.cuerpoactivo.cuerpo_activo_api.dto.PlanRequest;
import co.cuerpoactivo.cuerpo_activo_api.model.Plan;
import co.cuerpoactivo.cuerpo_activo_api.service.PlanService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import java.util.List;

@RestController
@RequestMapping("/api/planes")
public class PlanController {

    // private final List<Plan> planes = new ArrayList<>();
    private final PlanService planService;

    public PlanController(PlanService planService) {this.planService = planService;}

        //planes.add(new Plan(1L, "Plan mensual", 1, 120000));
        //planes.add(new Plan(2L, "Plan trimestral", 3, 330000));
        //planes.add(new Plan(3L, "Plan anual", 12, 1200000));

    @GetMapping
    public List<PlanResponse> listar() {
        return planService.listar()
                .stream()
                .map(PlanResponse::desde)
                .toList();
    }
    //public List<Plan> listar() { return planService.listar();

    @GetMapping("/{id}")
    public ResponseEntity<PlanResponse> buscarPorId(@PathVariable Long id) {
        Plan encontrado = planService.buscarPorId(id);

        return ResponseEntity.ok(PlanResponse.desde(encontrado));
    }
    // public Plan buscarPorId(@PathVariable Long id) { return planService.buscarPorId(id).orElse(null);}

    @PostMapping
    public ResponseEntity<PlanResponse> crear(@Valid @RequestBody PlanRequest request) {
        Plan creado = planService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(PlanResponse.desde(creado));
    }
    //public Plan crear(@RequestBody Plan nuevo) { return planService.crear(nuevo);}

    @PutMapping("/{id}")
    public ResponseEntity<PlanResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody PlanRequest request) {

        Plan actualizado = planService.actualizar(id, request);

        return ResponseEntity.ok(PlanResponse.desde(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!planService.eliminar(id)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}