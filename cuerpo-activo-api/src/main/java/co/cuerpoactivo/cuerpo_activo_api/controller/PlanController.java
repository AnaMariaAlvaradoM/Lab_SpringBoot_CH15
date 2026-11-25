package co.cuerpoactivo.cuerpo_activo_api.controller;

import co.cuerpoactivo.cuerpo_activo_api.dto.PlanRequest;
import co.cuerpoactivo.cuerpo_activo_api.dto.PlanResponse;
import co.cuerpoactivo.cuerpo_activo_api.model.Plan;
import co.cuerpoactivo.cuerpo_activo_api.repository.PlanRepository;
import co.cuerpoactivo.cuerpo_activo_api.service.PlanService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/planes")
public class PlanController {

//    private final List<Plan> planes = new ArrayList<>();
//
//    public PlanController() {
//        planes.add(new Plan(1L, "Plan mensual", 1, 120000));
//        planes.add(new Plan(2L, "Plan trimestral", 3, 330000));
//        planes.add(new Plan(3L, "Plan anual", 12, 1200000));
//    }
    private final PlanService planService;

    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    @GetMapping
    public List<PlanResponse> listar() {
//        return planes;
//        return planService.listar();
        List<PlanResponse> respuesta = new ArrayList<>();
        for (Plan plan : planService.listar()) {
            respuesta.add(PlanResponse.desde(plan));
        }
        return respuesta;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanResponse> buscarPorId(@PathVariable Long id) {
//        for (Plan plan: planes) {
//            if (plan.getId().equals(id)) {
//                return plan;
//            }
//        }
//        return null;

        Optional<Plan> encontrado = planService.buscarPorId(id);
        if (encontrado.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(PlanResponse.desde(encontrado.get()));
    }

    @PostMapping
    public ResponseEntity<PlanResponse> crear(@Valid @RequestBody PlanRequest request) {
//        nuevo.setId(planes.size() + 1L);
//        planes.add(nuevo);
//        return nuevo;

        Plan creado = planService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(PlanResponse.desde(creado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlanResponse> actualizar(@PathVariable Long id, @Valid @RequestBody PlanRequest request) {
        Optional<Plan> actualizado = planService.actualizar(id, request);
        if (actualizado.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(PlanResponse.desde(actualizado.get()));
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
