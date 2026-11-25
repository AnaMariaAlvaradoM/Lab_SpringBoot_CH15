
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
    public List<PlanResponse> listar() {
        return planService.buscarTodos()
                .stream()
                .map(PlanResponse::desde)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanResponse> buscarPorId(
            @PathVariable Long id) {

        PlanResponse respuesta = PlanResponse.desde(
                planService.buscarPorId(id)
        );

        return ResponseEntity.ok(respuesta);
    }

    @PostMapping
    public ResponseEntity<PlanResponse> crear(
            @Valid @RequestBody PlanRequest request) {

        PlanResponse respuesta = PlanResponse.desde(
                planService.crear(request)
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(respuesta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlanResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody PlanRequest cambios) {

        PlanResponse respuesta = PlanResponse.desde(
                planService.actualizar(id, cambios)
        );

        return ResponseEntity.ok(respuesta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {

        planService.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}
