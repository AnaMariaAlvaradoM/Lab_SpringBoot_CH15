package co.cuerpoactivo.cuerpo_activo_api.controller;

import co.cuerpoactivo.cuerpo_activo_api.dto.PlanRequest;
import co.cuerpoactivo.cuerpo_activo_api.dto.PlanResponse;
import co.cuerpoactivo.cuerpo_activo_api.service.PlanService;
import jakarta.validation.Valid;
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
    public ResponseEntity<List<PlanResponse>> obtenerTodos() {

        List<PlanResponse> planes = planService.buscarTodos()
                .stream()
                .map(PlanResponse::desde)
                .toList();

        return ResponseEntity.ok(planes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanResponse> obtenerPorId(@PathVariable Long id) {

        PlanResponse respuesta =
                PlanResponse.desde(planService.buscarPorId(id));

        return ResponseEntity.ok(respuesta);
    }

    @PostMapping
    public ResponseEntity<PlanResponse> guardar(
            @Valid @RequestBody PlanRequest request) {

        var planGuardado = planService.guardar(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(PlanResponse.desde(planGuardado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlanResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody PlanRequest request) {

        PlanResponse respuesta =
                PlanResponse.desde(planService.actualizar(id, request));

        return ResponseEntity.ok(respuesta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {

        planService.borrar(id);

        return ResponseEntity.noContent().build();
    }
}