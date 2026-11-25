package co.cuerpoactivo.cuerpo_activo_api.controller;

import co.cuerpoactivo.cuerpo_activo_api.dto.PlanRequest;
import co.cuerpoactivo.cuerpo_activo_api.dto.PlanResponse;
import co.cuerpoactivo.cuerpo_activo_api.model.Plan;
import co.cuerpoactivo.cuerpo_activo_api.service.PlanService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ArrayList;


@RestController
@RequestMapping("/api/planes")
public class PlanController {

    private final PlanService planService;

    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    @GetMapping
    public List<PlanResponse> listarPlanes() {
        List<Plan> planes = planService.listar();
        List<PlanResponse> respuesta = new ArrayList<>();
        for (Plan p : planes) {
            respuesta.add(PlanResponse.desde(p));
        }
        return respuesta;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanResponse> obtenerPlan(@PathVariable Long id) {
        Plan encontrado = planService.buscarPorId(id);
        return ResponseEntity.ok().body(PlanResponse.desde(encontrado));
    }

    @PostMapping
    public ResponseEntity<PlanResponse> crearPlan(@Valid @RequestBody PlanRequest nuevoPlan) {
        Plan creado = planService.crear(nuevoPlan);
        return ResponseEntity.status(HttpStatus.CREATED).body(PlanResponse.desde(creado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlanResponse> actualizar(@PathVariable Long id, @Valid @RequestBody PlanRequest request) {
        Plan actualizado = planService.actualizar(id, request);
        return ResponseEntity.ok().body(PlanResponse.desde(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        planService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
